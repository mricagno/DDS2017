package dondeInvierto.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import db.CuentaService;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;
import fileManagement.CuentaFromFile;
import fileManagement.FileHandler;

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

	@Path("/subirArchivo")
	@POST
	@Consumes("multipart/form-data")
	@Produces("text/plain")
	public Response recibirArchivo(@FormDataParam("file-0") InputStream uploadedInputStream,
			@FormDataParam("file-0") FormDataContentDisposition fileDetail) throws FileNotFoundException {

		CuentaFromFile cuentaActual;
		String uploadedFileLocation = ".//downloaded//" + fileDetail.getFileName();

		try {
			grabarArchivo(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e1) {
			throw new BadRequestException("No ha sido posible grabar el archivo en el disco.");
		}

		FileHandler fh = new FileHandler();
		List<CuentaFromFile> listaArchivo;

		listaArchivo = fh.dispatchParser(fh.readFile(uploadedFileLocation));

		for (int i = 0; i < listaArchivo.size(); i++) {
			cuentaActual = listaArchivo.get(i);

			try {
				mercado.addCuenta(cuentaActual.getNombre(), cuentaActual.getTipo(), cuentaActual.getPeriodo(),
						cuentaActual.getValor());
				EntityManager em = mercado.getFactory().createEntityManager();
				CuentaService cuenta_DB = new CuentaService(em);
				if ( null != mercado.getEmpresa(cuentaActual.getNombre()).getId()) {
					System.out.println(mercado.getEmpresa(cuentaActual.getNombre()).getNombre());
					System.out.println(mercado.getEmpresa(cuentaActual.getNombre()).getId());
					System.out.println(cuentaActual.getNombre() + "existe");
					cuenta_DB.addCuenta_existCompany(cuentaActual.getTipo(), cuentaActual.getPeriodo(), cuentaActual.getValor(),
							mercado.getEmpresa(cuentaActual.getNombre()));
				} else {
					System.out.println(cuentaActual.getNombre() + "no existe");
					System.out.println(cuentaActual.getNombre());
					cuenta_DB.addCuenta(cuentaActual.getTipo(), cuentaActual.getPeriodo(), cuentaActual.getValor(),
							new Empresa(cuentaActual.getNombre()));	
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return Response.status(200).build();
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
}