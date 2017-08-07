package json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import dondeInvierto.Indicador;

public class IndicadorAdapter implements JsonbAdapter<Indicador, JsonObject> {

	@Override
	public JsonObject adaptToJson(Indicador i) throws Exception {
		return Json.createObjectBuilder()
				.add("nombre", i.getNombre())
				.add("formula", i.getFormula())
				.build();
	}
	
	@Override
	public Indicador adaptFromJson(JsonObject obj) throws Exception {
		return new Indicador(
				obj.getString("nombre"), 
				obj.getString("formula"));
	}

}
