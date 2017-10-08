package dondeInvierto.resource;

import java.text.ParseException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import dondeInvierto.Usuario;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import dondeInvierto.resource.JsonError;
import dondeInvierto.resource.NotFoundException;

@Path("login")
public class UsuarioResource {
	private MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
	@GET
	@Produces("application/json")
	public void getUsuario(@PathParam("usuario") final String userID, @PathParam("password") final String password) 
			throws ParseException {
		/*JsonArrayBuilder usuarioBuilder = Json.createArrayBuilder();
		Usuario usuario = mercado.getUsuario(userID, "test");
		if (usuario.getId() != null) {
			usuarioBuilder.add(Json.createObjectBuilder().add("nombre", usuario.getUsuario()));
			return usuarioBuilder.build().toString();
		} 
		else {
			throw new NotFoundException(new JsonError("Error", "Usuario " + usuario + " no encontrado"));
		}*/

	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public String getUsuarioAll() throws ParseException {
		JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();
		for (Usuario usr : mercado.getUsuarios()) {
			jsonArrBuilder
					.add(Json.createObjectBuilder().add("usuario", usr.getUsuario()).add("password", usr.getPass()));
		}

		return jsonArrBuilder.build().toString();

	}
}