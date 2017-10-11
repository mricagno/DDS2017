package json;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import dondeInvierto.resource.CuentaResource;
import dondeInvierto.resource.EmpresaResource;
import dondeInvierto.resource.IndicadorResource;

@ApplicationPath("/")
public class JsonApplication extends ResourceConfig {
	public JsonApplication() {
        register(IndicadorResource.class);
        register(EmpresaResource.class);
        register(CuentaResource.class);
        register(MultiPartFeature.class);
	}
}
