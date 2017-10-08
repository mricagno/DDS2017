package dondeInvierto.resource;

import java.text.ParseException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import dondeInvierto.Usuario;
import dondeInvierto.MercadoBursatil;

@Path("usuarios")
public class usuarioResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@GET
	@Produces("application/json")
	public String getUsuario(@PathParam("usuario") final String userID, @PathParam("pass") final String password)
			throws ParseException {
		JsonArrayBuilder usuarioBuilder = Json.createArrayBuilder();
		Usuario usuario = mercado.getUsuario(userID, password);
		usuarioBuilder.add(Json.createObjectBuilder().add("nombre", usuario.getUsuario()));
		return usuarioBuilder.build().toString();
	}
}
