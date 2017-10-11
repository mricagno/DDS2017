package dondeInvierto.resource;

import java.io.StringReader;
import java.text.ParseException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import dondeInvierto.Usuario;
import dondeInvierto.MercadoBursatil;

@Path("login")
public class UsuarioResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

	@Path("/auth")
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response getUsuario(String jsonInput) throws ParseException {

		JsonObject json = (JsonObject) Json.createReader(new StringReader(jsonInput)).read();
		String usuario = json.getString("usuario");
		String password = json.getString("password");

		if (mercado.getUsuario(usuario, password) == null) {
			throw new BadRequestException("No se encontró el usuario");

		}
		Usuario user = mercado.getUsuario(usuario, password);
		mercado.setUsuario(user);
		return Response.accepted().build();
	}

}