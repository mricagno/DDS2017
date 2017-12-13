package dondeInvierto.resource;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import db.IndicadorService;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;

@Path("indicadores")
public class IndicadorResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getIndicadores() {
		JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();
		EntityManager em = mercado.getFactory().createEntityManager();
		IndicadorService indicador_DB = new IndicadorService(em);
		mercado.setIndicadores(indicador_DB.listIndicadores());
		em.close();
		for (Indicador ind : mercado.getIndicadores()) {
			if (mercado.getUsuarioLog().getUsuario().equals(ind.getCreador()) || ind.getCreador().equals("DEFAULT")) {
				jsonArrBuilder.add(
						Json.createObjectBuilder().add("nombre", ind.getNombre()).add("formula", ind.getFormula()));
			}
		}
		return jsonArrBuilder.build().toString();
	}

	@Path("/{indicador}")
	@GET
	@Produces("application/json")
	public String getIndicador(@PathParam("indicador") final String indicador) {
		Indicador ind = mercado.getIndicador(indicador.replaceAll("%20", " "));
		return Json.createObjectBuilder().add("nombre", ind.getNombre()).add("formula", ind.getFormula()).build()
				.toString();
	}

	@Path("/nuevo")
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response createIndicador(String indicador) {
		JsonObject json = (JsonObject) Json.createReader(new StringReader(indicador)).read();
		String formula = json.getString("nombre") + " = " + json.getString("formula");
		System.out.println(mercado.getUsuarioLog().getUsuario());
		if (!mercado.addIndicador(json.getString("nombre"), formula, mercado.getUsuarioLog().getUsuario())) {
			return Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("El indicador ya existe.").build();
		} else {
			try {
				EntityManager em = mercado.getFactory().createEntityManager();
				IndicadorService indicador_DB = new IndicadorService(em);
				indicador_DB.addIndicador(
						new Indicador(json.getString("nombre"), formula, mercado.getUsuarioLog().getUsuario()));
				em.close();
				return Response.accepted(json.getString("nombre")).build();
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();
				return Response.serverError().entity("Se produjo un error al intentar generar la respuesta al cliente.").build();
			}
		}	
	}
	
	@Path("/indicadorCalculado")
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public String getIndicadorCalculado(String jsonInput) {
		JsonObject json = (JsonObject) Json.createReader(new StringReader(jsonInput)).read();
		System.out.println("ansofnoa");
		String empresaCalculo = json.getString("empresaCalculo");
		String periodoCalculo = json.getString("periodoCalculo");
		String indicadorCalculo = json.getString("indicadorCalculo");
		Empresa empresa = mercado.getEmpresa(empresaCalculo);
		Indicador indicador = mercado.getIndicador(indicadorCalculo);
		double valor_indicador = mercado.getIndicadorCalculado(empresa.getId(), indicador.getId(), periodoCalculo);
		return Json.createObjectBuilder().add("valor_indicador", valor_indicador).build()
				.toString();
	}

	@Path("/borrar/{nombre}")
	@DELETE
	@Consumes("application/json")
	@Produces("text/plain")
	public Response borrarIndicador(@PathParam("nombre") final String indicador) {
		String nombre = indicador;
		Indicador indicador_borrar = mercado.getIndicador(nombre);
		if (mercado.delete_Indicador(nombre)) {
			EntityManager em = mercado.getFactory().createEntityManager();
			IndicadorService indicador_DB = new IndicadorService(em);
			indicador_DB.deleteIndicador(indicador_borrar.getId());
			em.close();
		} else {
			throw new BadRequestException("El indicador no ha sido borrado.");
		}
		return Response.noContent().build();
	}

}
