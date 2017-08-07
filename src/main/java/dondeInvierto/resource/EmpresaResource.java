package dondeInvierto.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dondeInvierto.Empresa;
import dondeInvierto.MercadoBursatil;

@Path("empresas")
public class EmpresaResource {
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> getEmpresas() {
        return mercado.getEmpresas();
    }
     
    @GET
    @Path("/{empresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa getCuenta(@PathParam("empresa") final String empresa) {
        return mercado.getEmpresa(empresa);
    }
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public String showText() {
        return "Prueba";
    }
}