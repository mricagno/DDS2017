package dondeInvierto.resource;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import db.CondicionService;
import db.IndicadorService;
import db.MetodologiaService;
import dondeInvierto.Metodologia;
import dondeInvierto.ResultadoCondicionado;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;

@Path("metodologias")
public class MetodologiaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getMetodologias() {	
		String comparador = "";
		JsonArrayBuilder metArrBuilder = Json.createArrayBuilder();
		EntityManager em = mercado.getFactory().createEntityManager();
		MetodologiaService metodologias_DB = new MetodologiaService(em);
		CondicionService condicion_DB = new CondicionService(em);
		mercado.setMetodologias(metodologias_DB.getMetodologias());
		for (Metodologia m : mercado.getMetodologias()) {
			for (CondicionFiltro f : m.getCondicionesFiltro()) {
				f.setEmpresas(mercado.getEmpresas());
			}
			for (CondicionOrdenamiento o : m.getCondicionesOrdenamiento()) {
				o.setEmpresas(mercado.getEmpresas());
			}
		}
		//em.close();
		for(Metodologia met : mercado.getMetodologias()) {
			JsonArrayBuilder condArrBuilder = Json.createArrayBuilder();
			if (mercado.getUsuarioLog().getUsuario().equals(met.getCreador()) || met.getCreador().equals("DEFAULT")) {
				for(CondicionFiltro cf : met.getCondicionesFiltro()) {
					if (cf.getComparador().equals("filtrarAntiguedadMenoroigual") || cf.getComparador().equals("filtrarAntiguedadMenor") || 
							cf.getComparador().equals("filtrarAntiguedadMayoroigual") || cf.getComparador().equals("filtrarAntiguedadMayor") ||
							cf.getComparador().equals("filtrarAntiguedadDiferente") || cf.getComparador().equals("filtrarAntiguedadIgual")) {
						switch (cf.getComparador()) {
							case "filtrarAntiguedadMenoroigual":
								comparador = "<=";
								break;
							case "filtrarAntiguedadMenor":
								comparador = "<";
								break;
							case "filtrarAntiguedadMayoroigual":
								comparador = ">=";
								break;
							case "filtrarAntiguedadMayor":
								comparador = ">";
								break;
							case "filtrarAntiguedadDiferente":
								comparador = "<>";
								break;
							case "filtrarAntiguedadIgual":
								comparador = "=";
								break;
						}	
						condArrBuilder.add(
								Json.createObjectBuilder()
										.add("tipo", "Filtro")
										.add("nombre", cf.getNombre())
										.add("indicador", "Antigüedad")
										.add("filtro", comparador + " " + (int) cf.getValor() ));
					} else {
						condArrBuilder.add(
								Json.createObjectBuilder()
										.add("tipo", "Filtro")
										.add("nombre", cf.getNombre())
										.add("indicador", condicion_DB.getCondicionFiltro(cf.getIndicador()).getNombre())
										.add("filtro", cf.getComparador() + " " + (int) cf.getValor() ));
					}	
				}
				for(CondicionOrdenamiento co : met.getCondicionesOrdenamiento()) {
					condArrBuilder.add(
							Json.createObjectBuilder()
									.add("tipo", "Ordenamiento")
									.add("nombre", co.getNombre())
									.add("indicador", condicion_DB.getCondicionOrdenamiento(co.getIndicador()).getNombre())
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
    	
    	Set<CondicionFiltro> condicionesFiltro = new HashSet<>();
    	Set<CondicionOrdenamiento> condicionesOrdenamiento = new HashSet<>();
		
    	JsonArray condiciones = json.getJsonArray("condiciones");
    	
    	for (JsonValue jo : condiciones) {
    		JsonObject cond = (JsonObject) Json.createReader(new StringReader(jo.toString())).read();
    		
    		if (cond.getString("tipo").equals("Filtro")) {
    			condicionesFiltro.add(
    					new CondicionFiltro(
    							cond.getString("nombre"),
    							cond.getString("criterio"),
    							(double) Double.parseDouble(cond.getString("valor")),
								mercado.getIndicador(cond.getString("indicador")).getId()
    					)
    				);    			
    		} else {
    			condicionesOrdenamiento.add(
    					new CondicionOrdenamiento(
    							cond.getString("nombre"),
    							cond.getString("criterio"),
    							(double) Double.parseDouble(cond.getString("valor")),
								mercado.getIndicador(cond.getString("indicador")).getId()
    					)
    				);
    		}
    	}
    	
    	for (CondicionFiltro cf : condicionesFiltro) {
    		cf.setEmpresas(mercado.getEmpresas());
    	}
    	
    	for (CondicionOrdenamiento co : condicionesOrdenamiento) {
    		co.setEmpresas(mercado.getEmpresas());
    	}
    	
    	try {
    		EntityManager em = mercado.getFactory().createEntityManager();
			MetodologiaService metodologia_DB = new MetodologiaService(em);
			metodologia_DB.setMetodologia(
					json.getString("nombre"),
					condicionesFiltro,
					condicionesOrdenamiento,
					mercado.getUsuarioLog().getUsuario());
			//em.close();
			return Response.accepted(json.getString("nombre")).build();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.err.println("Error en el registro de la metodologia");
    		return Response.serverError().entity("Se produjo un error al intentar generar la respuesta al cliente.").build();
    	}
    }
    
    @Path("/evaluar/{metodologia}")
    @GET
    @Produces("application/json")
    public String evaluateMetodologia(@PathParam("metodologia") final String metodologia) {
    	JsonArrayBuilder empresasArrBuilder = Json.createArrayBuilder();
	    Metodologia metodologiaEvaluada = mercado.getMetodologia(metodologia.replaceAll("%20", " "));
	    metodologiaEvaluada.calcularMetodologia();
	    for (ResultadoCondicionado rc : metodologiaEvaluada.getListaFiltradaUOrdenada()) {
	    	empresasArrBuilder.add(
	    			Json.createObjectBuilder()
						.add("nombre", rc.getNombre()));
	    }
	    return empresasArrBuilder.build().toString();
    }
    
}