package dondeInvierto.resource;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import db.MetodologiaService;
import dondeInvierto.Metodologia;
import dondeInvierto.ResultadoCondicionado;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.MercadoBursatil;

@Path("metodologias")
public class MetodologiaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getMetodologias() {	
		JsonArrayBuilder metArrBuilder = Json.createArrayBuilder();
		EntityManager em = mercado.getFactory().createEntityManager();
		MetodologiaService metodologias_DB = new MetodologiaService(em);
		mercado.setMetodologias(metodologias_DB.getMetodologias());
		for (Metodologia m : mercado.getMetodologias()) {
			for (CondicionFiltro f : m.getCondicionesFiltro()) {
				f.setEmpresas(mercado.getEmpresas());
			}
			for (CondicionOrdenamiento o : m.getCondicionesOrdenamiento()) {
				o.setEmpresas(mercado.getEmpresas());
			}
		}
		em.close();
		for(Metodologia met : mercado.getMetodologias()) {
			JsonArrayBuilder condArrBuilder = Json.createArrayBuilder();
			if (mercado.getUsuarioLog().getUsuario().equals(met.getCreador()) || met.getCreador().equals("DEFAULT")) {
				for(CondicionFiltro cf : met.getCondicionesFiltro()) {
					condArrBuilder.add(
							Json.createObjectBuilder()
									.add("tipo", "Filtro")
									.add("nombre", cf.getNombre())
									.add("indicador", cf.getIndicador().getNombre())
									.add("filtro", cf.getComparador()));
				}
				for(CondicionOrdenamiento co : met.getCondicionesOrdenamiento()) {
					condArrBuilder.add(
							Json.createObjectBuilder()
									.add("tipo", "Ordenamiento")
									.add("nombre", co.getNombre())
									.add("indicador", co.getIndicador().getNombre())
									.add("orden", co.getComparador()));
				}
				metArrBuilder.add(
						Json.createObjectBuilder().add("nombre", met.getNombre()).add("condiciones", condArrBuilder));
			}
		}
		return metArrBuilder.build().toString();
	}
    
    @Path("/{metodologia}")
    @GET
	@Produces("application/json")
    public String getMetodologia(@PathParam("metodologia") final String metodologia) {
        Metodologia met = mercado.getMetodologia(metodologia.replaceAll("%20", " "));
        return Json.createObjectBuilder()
        		.add("nombre", met.getNombre())
        		/*.add("formula", ind.getFormula())  */  
        		
        		.build().toString();
    }
    
    @Path("/nueva")
    @POST
	@Consumes("application/json")
    @Produces("text/plain")
    public Response createMetodologia(String metodologia) {
	    JsonObject json = (JsonObject) Json.createReader(new StringReader(metodologia)).read();
	    String nombre = json.getString("nombre");
	    System.out.println(mercado.getUsuarioLog().getUsuario());
	    	/*if (!mercado.addMetodologia(json.getString("nombre"), condicionesFiltro, condicionesOrdenamiento, mercado.getUsuarioLog().getUsuario())) {
	    		throw new BadRequestException("El indicador no ha sido creado.");
	    	}else {
				EntityManager em = mercado.getFactory().createEntityManager();
				MetodologiaService metodologia_DB = new MetodologiaService(em);
				metodologia_DB.setMetodologia(json.getString("nombre"), condicionesFiltro, condicionesOrdenamiento, mercado.getUsuarioLog().getUsuario());
				em.close();
	    	}
	    	return Response.created(URI.create(json.getString("nombre"))).build();
	    }
	    
	    JsonObject json = (JsonObject) Json.createReader(new StringReader(indicador)).read();
			String formula = json.getString("nombre") + " = " + json.getString("formula");
			System.out.println(mercado.getUsuarioLog().getUsuario());
			if (!mercado.addIndicador(json.getString("nombre"), formula, mercado.getUsuarioLog().getUsuario())) {
				throw new BadRequestException("El indicador no ha sido creado.");
			} else {
				EntityManager em = mercado.getFactory().createEntityManager();
				IndicadorService indicador_DB = new IndicadorService(em);
				indicador_DB.addIndicador(
						new Indicador(json.getString("nombre"), formula, mercado.getUsuarioLog().getUsuario()));
				em.close();
			}
			return Response.created(URI.create(json.getString("nombre"))).build();
	    */
		return null;
    }
    
    @Path("/evaluar/{metodologia}")
    @GET
    @Produces("application/json")
    public String evaluateMetodologia(@PathParam("metodologia") final String metodologia) {
    	JsonArrayBuilder empresasArrBuilder = Json.createArrayBuilder();
	    Metodologia metodologiaEvaluada = mercado.getMetodologia(metodologia);
	    metodologiaEvaluada.calcularMetodologia();
	    for (ResultadoCondicionado rc : metodologiaEvaluada.getListaFiltradaUOrdenada()) {
	    	empresasArrBuilder.add(
	    			Json.createObjectBuilder()
						.add("nombre", rc.getNombre()));
	    }
	    return empresasArrBuilder.build().toString();
    }
    
}