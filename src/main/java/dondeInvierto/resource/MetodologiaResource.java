package dondeInvierto.resource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import db.MetodologiaService;
import dondeInvierto.Metodologia;
import dondeInvierto.CondicionFiltro;
import dondeInvierto.CondicionOrdenamiento;
import dondeInvierto.MercadoBursatil;

@Path("metodologias")
public class MetodologiaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getMetodologias() {	
		JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();   
		System.out.println("test");
		EntityManager em = mercado.getFactory().createEntityManager();
		MetodologiaService metodologias_DB = new MetodologiaService(em);
		mercado.setMetodologias(metodologias_DB.getMetodologias());
		em.close();
		for(Metodologia met : mercado.getMetodologias()) {
			jsonArrBuilder.add(Json.createObjectBuilder()
					.add("nombre", met.getNombre())
					);
			System.out.println(met.getNombre());
			/*for(CondicionFiltro filt : met.getCondicionesFiltro()) {	
				jsonArrBuilder.add(Json.createObjectBuilder()
						.add("condicionesFiltro", filt.getNombre())
						);	
				System.out.println(filt.getNombre());
					}	
			for(CondicionOrdenamiento Ord : met.getCondicionesOrdenamiento()) {	
				jsonArrBuilder.add(Json.createObjectBuilder()
						.add("condicionesOrdenamiento", Ord.getNombre())
						);	
				System.out.println(Ord.getNombre());
					}*/
			
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
    
    /*@Path("/nuevo")
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
	//return null;
    
//}
    
}