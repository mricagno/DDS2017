package dondeInvierto.resource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import json.IndicadorAdapter;

@Path("indicadores")
public class IndicadorResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	private JsonbConfig config = new JsonbConfig().withAdapters(new IndicadorAdapter());

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
    	Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(mercado.getIndicador(indicador.replaceAll("%20", " ")));
    }
}