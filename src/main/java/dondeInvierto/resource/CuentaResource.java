package dondeInvierto.resource;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import db.CuentaService;
import db.EmpresaService;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;
import fileManagement.CuentaFromFile;
import fileManagement.FileHandler;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import seguridad.CustomRevEntity;

@Path("cuentas")
public class CuentaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getCuentas() {
		JsonArrayBuilder cuentasBuilder = Json.createArrayBuilder();

		for (Empresa emp : mercado.getEmpresas()) {
			for (Cuenta c : emp.getCuentas()) {
				cuentasBuilder.add(Json.createObjectBuilder().add("empresa", emp.getNombre()).add("tipo", c.getTipo())
						.add("periodo", c.getPeriodoAsString()).add("valor", c.getValor()));
			}
		}
		return cuentasBuilder.build().toString();
	}

	/**
	 * Clase para cargar cuenta individual
	 */
	@Path("/nuevaCuenta")
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response createCuenta(String jsonInput) throws Exception {
		JsonObject json = (JsonObject) Json.createReader(new StringReader(jsonInput)).read();
		String nombreEmpresa = json.getString("nombreEmpresa");
		String tipoCuenta = json.getString("tipoCuenta");
		String periodoCuenta = json.getString("periodoCuenta");
		String valorCuenta = json.getString("valorCuenta");
		if (!nombreEmpresa.isEmpty() && !tipoCuenta.isEmpty() && !periodoCuenta.isEmpty() && !valorCuenta.isEmpty()) {
			EntityManager em = mercado.getFactory().createEntityManager();
			EmpresaService empresa = new EmpresaService(em);
			CuentaFromFile cuentaActual = new CuentaFromFile();
			cuentaActual.setNombre(nombreEmpresa);
			cuentaActual.setTipo(tipoCuenta);
			cuentaActual.setPeriodo(periodoCuenta);
			cuentaActual.setValor(valorCuenta);
			try {
				/**
				 * Se agrega la cuenta a la empresa El nombre de la empresa se
				 * obtiene desde el metodo getNombre()
				 */
				CuentaService cuenta_DB = new CuentaService(em);
				mercado.addCuenta(cuentaActual.getNombre(), cuentaActual.getTipo(), cuentaActual.getPeriodo(),
						cuentaActual.getValor());
				if (mercado.getEmpresa(cuentaActual.getNombre()).getId() != null) {
					/**
					 * Si la cuenta pertenece a una empresa que ya esta creada
					 * se agrega en caso de no existir o se actualiza el valor
					 */
					if (mercado
							.getCuenta(new Cuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo().toString(),
									cuentaActual.getValor()), mercado.getEmpresa(cuentaActual.getNombre()))
							.getId() != null) {
						/**
						 * En caso de existir la cuenta, se actualiza su valor
						 */
						cuenta_DB
								.updateCuenta2(
										mercado.getCuenta(
												new Cuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo().toString(),
														cuentaActual.getValor()),
												mercado.getEmpresa(cuentaActual.getNombre())).getId(),
										Double.parseDouble(cuentaActual.getValor()));
					} else {
						/**
						 * En caso de no existir la cuenta, se agrega a la
						 * empresa
						 */
						System.out.println("test3");
						cuenta_DB.addCuenta_existCompany(cuentaActual.getTipo(), cuentaActual.getPeriodo(),
								cuentaActual.getValor(), mercado.getEmpresa(cuentaActual.getNombre()));
					}

				} else {
					/**
					 * En caso de no existir la empresa, se persiste antes
					 */
					empresa.addEmpresa(cuentaActual.getNombre());
					mercado.setEmpresas(empresa.listEmpresas());
					cuenta_DB.addCuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo(), cuentaActual.getValor(),
							mercado.getEmpresa(cuentaActual.getNombre()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mercado.init_model(em);
			mercado.preCalculo_indicadores();
		}
		return Response.status(200).build();
	}

	/**
	 * Clase para cargar archivo desde web
	 */
	@Path("/subirArchivo")
	@POST
	@Consumes("multipart/form-data")
	@Produces("text/plain")
	public Response recibirArchivo(@FormDataParam("file-0") InputStream uploadedInputStream,
			@FormDataParam("file-0") FormDataContentDisposition fileDetail) throws Exception {

		String uploadedFileLocation = ".//downloaded//" + fileDetail.getFileName();

		try {
			grabarArchivo(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e1) {
			return Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("Se ha producido un error al intentar grabar el archivo en disco.").build();
		}

		FileHandler fh = new FileHandler();
		List<CuentaFromFile> listaArchivo;

		listaArchivo = fh.dispatchParser(fh.readFile(uploadedFileLocation));
		/**
		 * Se recorren las cuentas que se obtuvieron
		 */
		EntityManager em = mercado.getFactory().createEntityManager();
		mercado.set_lastFileLoaded(fileDetail.getFileName());
		this.cargar_cuentas(em, listaArchivo);
		try {
			mercado.init_model(em);
		} catch (Exception e) {
			e.printStackTrace();
			Response.serverError().status(Response.Status.BAD_GATEWAY)
					.entity("Se ha producido un error al intentar obtener la información de la base de datos").build();
		}
		return Response.accepted("El archivo se procesó correctamente.").build();
	}

	private void grabarArchivo(InputStream uploadedInputStream, String uploadedFileLocation)
			throws FileNotFoundException, IOException {

		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(uploadedFileLocation));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}

	public Response read_file() throws Exception {

		EntityManager em = mercado.getFactory().createEntityManager();
		String fileToRead = this.getFilesFromDirectory(em);
		if (fileToRead != null) {

			FileHandler fh = new FileHandler();
			List<CuentaFromFile> listaArchivo;
			String uploadedFileLocation = mercado.getPath_carga_cuentas() + fileToRead;
			listaArchivo = fh.dispatchParser(fh.readFile(uploadedFileLocation));
			/**
			 * Se recorren las cuentas que se obtuvieron
			 */
			mercado.set_lastFileLoaded(fileToRead);
			this.cargar_cuentas(em, listaArchivo);
			mercado.init_model(em);
			/*
			 * try { if (!mercado.getEmpresas().isEmpty()) {
			 */
			mercado.preCalculo_indicadores();
			/*
			 * } mercado.init_model(em); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}
		return Response.status(200).build();

	}

	public void cargar_cuentas(EntityManager em, List<CuentaFromFile> listaArchivo) {

		EmpresaService empresa = new EmpresaService(em);
		/**
		 * Se recorren las cuentas que se obtuvieron
		 */
		CuentaFromFile cuentaActual;
		for (int i = 0; i < listaArchivo.size(); i++) {
			cuentaActual = listaArchivo.get(i);

			try {
				/**
				 * Se agrega la cuenta a la empresa El nombre de la empresa se
				 * obtiene desde el metodo getNombre()
				 */
				CuentaService cuenta_DB = new CuentaService(em);
				mercado.addCuenta(cuentaActual.getNombre(), cuentaActual.getTipo(), cuentaActual.getPeriodo(),
						cuentaActual.getValor());
				if (mercado.getEmpresa(cuentaActual.getNombre()).getId() != null) {
					/**
					 * Si la cuenta pertenece a una empresa que ya esta creada
					 * se agrega en caso de no existir o se actualiza el valor
					 */
					if (mercado
							.getCuenta(new Cuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo().toString(),
									cuentaActual.getValor()), mercado.getEmpresa(cuentaActual.getNombre()))
							.getId() != null) {
						/**
						 * En caso de existir la cuenta, se actualiza su valor
						 */
						cuenta_DB
								.updateCuenta2(
										mercado.getCuenta(
												new Cuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo().toString(),
														cuentaActual.getValor()),
												mercado.getEmpresa(cuentaActual.getNombre())).getId(),
										Double.parseDouble(cuentaActual.getValor()));
					} else {
						/**
						 * En caso de no existir la cuenta, se agrega a la
						 * empresa
						 */
						cuenta_DB.addCuenta_existCompany(cuentaActual.getTipo(), cuentaActual.getPeriodo(),
								cuentaActual.getValor(), mercado.getEmpresa(cuentaActual.getNombre()));
					}
				} else {

					/**
					 * En caso de no existir la empresa, se persiste antes
					 */
					empresa.addEmpresa(cuentaActual.getNombre());
					mercado.setEmpresas(empresa.listEmpresas());
					cuenta_DB.addCuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo(), cuentaActual.getValor(),
							mercado.getEmpresa(cuentaActual.getNombre()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFilesFromDirectory(EntityManager em) {

		File[] results;
		File directorio = new File(mercado.getPath_carga_cuentas());
		results = directorio.listFiles();
		/**
		 * Se busca la lista de archivos disponibles Se leen unicamente los
		 * archivos .json
		 */
		results = directorio.listFiles((dir, name) -> name.endsWith(".json"));
		List<String> listOfFilesAvailable = new ArrayList<>();
		for (int i = 0; i < results.length; i++) {
			if (results[i].isFile()) {
				listOfFilesAvailable.add(results[i].getName());
			}
		}

		AuditReader reader = AuditReaderFactory.get(em);
		List<Empresa> empresas = mercado.getEmpresas();
		final List<String> readedFiles = new ArrayList<>();
		empresas.forEach(empresa -> {
			empresa.getCuentas().forEach(cuenta -> {
				List<Number> revisions = reader.getRevisions(Cuenta.class, cuenta.getId());
				Iterator listIterator = revisions.listIterator();
				while (listIterator.hasNext()) {
					CustomRevEntity entity = em.find(CustomRevEntity.class, listIterator.next());
					readedFiles.add(entity.getUsed_file());
				}
			});
		});
		Set<String> list1 = new HashSet<String>(listOfFilesAvailable);
		Set<String> list2 = new HashSet<String>(readedFiles);
		list1.removeAll(list2);
		return list1.stream().findFirst().orElse(null);
	}

}