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
import dondeInvierto.MercadoBursatil;

@Path("metodologias")
public class MetodologiaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getIndicadores() {	
		JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();   
		EntityManager em = mercado.getFactory().createEntityManager();
		MetodologiaService metodologias_DB = new MetodologiaService(em);
		mercado.setMetodlogias(metodologias_DB.getMetodologias());
		em.close();
		for(Metodologia met : mercado.getMetodologias()) {
			jsonArrBuilder.add(Json.createObjectBuilder()
					.add("nombre", met.getNombre())
/*					.add("CondicionesFiltro", (JsonValue) met.getCondicionesFiltro())
					.add("CondicionesOrdenamiento", (JsonValue) met.getCondicionesOrdenamiento()*/
					);
		}
		return jsonArrBuilder.build().toString();
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
    
    @Path("/nuevo")
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
    }*/
	return null;
    
}}