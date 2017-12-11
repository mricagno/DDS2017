package dondeInvierto.resource;

import java.io.StringReader;
import java.text.ParseException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

@Path("config")
public class AppConfigResource {
    private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

    @Path("/setData")
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response setData(String jsonInput) throws ParseException {

        JsonObject json = (JsonObject) Json.createReader(new StringReader(jsonInput)).read();
        String path = json.getString("path_cuentas");
        String intervalo = json.getString("intervalo_cuentas");
        if (!path.isEmpty() || (intervalo.isEmpty())) {
            mercado.setPath_carga_cuentas(path);
            mercado.setIntervalo_carga_cuentas(Integer.parseInt(intervalo));
            return Response.accepted().build();
        } else {
            return Response.notModified().build();
        }
    }

    @GET
    @Path("/getData")
    @Produces("application/json")
    public String getCuentas() {
        JsonArrayBuilder configData = Json.createArrayBuilder();
        configData.add(Json.createObjectBuilder().add("path", mercado.getPath_carga_cuentas()).
                add("intervalo", mercado.getIntervalo_carga_cuentas()));
        return configData.build().toString();
    }
}