package dondeInvierto.resource;

import javax.ws.rs.core.MediaType;

/*import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import dondeInvierto.Cuenta;*/
import dondeInvierto.MercadoBursatil;

/*@Path("cuentas")
@Produces(MediaType.APPLICATION_JSON)*/
public class CuentaResource {
	MercadoBursatil mercado = MercadoBursatil.INSTANCE;
	/*
    @GET
    public List<Cuenta> getCuentas() {
        return 
    }
     
    @GET
    @Path("/{cuenta}")
    public Cuenta getCuenta(@PathParam("cuenta") final Cuenta cuenta) {
        return mercado.getCuenta(cuenta);
    }*/
}
