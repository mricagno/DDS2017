package json;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

//import dondeInvierto.MercadoBursatil;
import dondeInvierto.resource.CuentaResource;
import dondeInvierto.resource.EmpresaResource;
import dondeInvierto.resource.IndicadorResource;
import dondeInvierto.resource.MetodologiaResource;
import dondeInvierto.resource.UsuarioResource;
import dondeInvierto.resource.AppConfigResource;

@ApplicationPath("/")
public class JsonApplication extends ResourceConfig {
	public JsonApplication() throws Exception {
//		MercadoBursatil mercado = MercadoBursatil.INSTANCE;	
//		mercado.init();
		
        register(IndicadorResource.class);
        register(EmpresaResource.class);
        register(CuentaResource.class);
        register(UsuarioResource.class);
        register(MetodologiaResource.class);
        register(MultiPartFeature.class);
        register(AppConfigResource.class);
        
	}
}
