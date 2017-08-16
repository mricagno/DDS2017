package dondeInvierto.resource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

@Path("empresas")
public class EmpresaResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
	@GET
	@Produces("application/json")
    public String getEmpresas() {
		JsonArrayBuilder empresasBuilder = Json.createArrayBuilder();   
		
		for(Empresa emp : mercado.getEmpresas()) {
			JsonArrayBuilder cuentasBuilder = Json.createArrayBuilder();
			
			for(Cuenta c : emp.getCuentas()) {
				cuentasBuilder.add(Json.createObjectBuilder()
						.add("tipo", c.getTipo())
						.add("periodo", c.getPeriodoAsString())
						.add("valor", c.getValor()));
			}
			empresasBuilder.add(Json.createObjectBuilder()
					.add("nombre", emp.getNombre())
					.add("cuentas", cuentasBuilder));
		}
		return empresasBuilder.build().toString();
    }
    
	@Path("/{empresa}")
	@GET
	@Produces("application/json")
    public String getEmpresa(@PathParam("empresa") final String empresa) {
		Empresa emp = mercado.getEmpresa(empresa.replaceAll("%20", " "));
		JsonArrayBuilder cuentasBuilder = Json.createArrayBuilder();
		
		for(Cuenta c : emp.getCuentas()) {
			cuentasBuilder.add(Json.createObjectBuilder()
					.add("tipo", c.getTipo())
					.add("periodo", c.getPeriodoAsString())
					.add("valor", c.getValor()));
		}
		
		return Json.createObjectBuilder()
				.add("nombre", emp.getNombre())
				.add("cuentas", cuentasBuilder)
				.build().toString();
    }
	
	/*@Path("/{empresa}/{cuenta}/{periodo}/")
	@GET
	@Produces("application/json")
    public Empresa getCuenta(@PathParam("empresa") final String empresa) {
        Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.toJson(mercado.getEmpresa(empresa.replaceAll("%20", " ")));
    }*/
}