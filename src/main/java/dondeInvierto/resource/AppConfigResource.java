package dondeInvierto.resource;

import java.io.StringReader;
import java.text.ParseException;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import db.IndicadorService;
import dondeInvierto.Cuenta;
import dondeInvierto.Empresa;
import dondeInvierto.Indicador;
import dondeInvierto.MercadoBursatil;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import seguridad.CustomRevEntity;

//btn-guardar-config
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

    @Path("/log")
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response callLog(String jsonInput) throws ParseException {

        return Response.notModified().build();
    }

    @GET
    @Path("/getLog")
    @Produces("application/json")
    public String getLog() throws ParseException {

        EntityManager em = mercado.getFactory().createEntityManager();
        AuditReader reader = AuditReaderFactory.get(em);
        List<Empresa> empresas = mercado.getEmpresas();
        System.out.println("teasfga");
        final List<String> readedFiles = new ArrayList<>();
        empresas.forEach(empresa -> {
            empresa.getCuentas().forEach(cuenta -> {
                List<Number> revisions = reader.getRevisions(Cuenta.class,
                        cuenta.getId());
                Iterator listIterator = revisions.listIterator();
                while (listIterator.hasNext()) {
                    CustomRevEntity entity = em.find(CustomRevEntity.class, listIterator.next());
                    readedFiles.add(entity.getUsed_file());
                }
            });
        });
        List<String> list3 = new ArrayList<>(new HashSet<>(readedFiles));
        JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();
        for (int i = 1; i < list3.size(); i++) {
            jsonArrBuilder.add(Json.createObjectBuilder().add("archivo", list3.get(i)));
        }
        em.close();
        return jsonArrBuilder.build().toString();
    }
}
