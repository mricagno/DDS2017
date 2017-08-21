package dondeInvierto.resource;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;

@Path("indicadores")
public class IndicadorResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getIndicadores() {	
		JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();   
		
		for(Indicador ind : mercado.getIndicadores()) {
			jsonArrBuilder.add(Json.createObjectBuilder()
					.add("nombre", ind.getNombre())
					.add("formula", ind.getFormula()));
		}
		
		return jsonArrBuilder.build().toString();
	}
    
    @Path("/{indicador}")
    @GET
	@Produces("application/json")
    public String getIndicador(@PathParam("indicador") final String indicador) {
        Indicador ind = mercado.getIndicador(indicador.replaceAll("%20", " "));
        //return {errors=...
        //Ver https://dev.twitter.com/overview/api/response-codes
        return Json.createObjectBuilder()
        		.add("nombre", ind.getNombre())
        		.add("formula", ind.getFormula())
        		.build().toString();
    }
    
    @Path("/nuevo")
    @POST
	@Consumes("application/json")
    public Response createIndicador(String indicador) {
    	JsonObject json = (JsonObject) Json.createReader(new StringReader(indicador)).read();
    	String formula = json.getString("nombre") + " = " + json.getString("formula"); 	
    	
    	try {
    		mercado.addIndicador(json.getString("nombre"), formula);
    		return Response.ok().build();
    	} catch (Exception e) {
			e.printStackTrace();
			return Response.notModified().build();
		}
        //return {errors=...
        //Ver https://dev.twitter.com/overview/api/response-codes
    }
}