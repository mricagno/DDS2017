package dondeInvierto.resource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

@Path("cuentas")
public class CuentaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
	@GET
	@Produces("application/json")
	public String getCuentas() {	
		JsonArrayBuilder cuentasBuilder = Json.createArrayBuilder();
		
		for(Empresa emp : mercado.getEmpresas()) {
			for(Cuenta c : emp.getCuentas()) {
				cuentasBuilder.add(Json.createObjectBuilder()
						.add("empresa", emp.getNombre())
						.add("tipo", c.getTipo())
						.add("periodo", c.getPeriodoAsString())
						.add("valor", c.getValor()));
			}
		}	
		return cuentasBuilder.build().toString();
	}
}
