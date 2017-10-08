package dondeInvierto.resource;

import java.io.StringReader;
import java.net.URI;
import java.text.ParseException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import dondeInvierto.Usuario;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import dondeInvierto.resource.JsonError;
import dondeInvierto.resource.NotFoundException;

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