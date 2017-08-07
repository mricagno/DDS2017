package json;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import dondeInvierto.resource.IndicadorResource;

@ApplicationPath("/")
public class JsonApplication extends ResourceConfig {
	public JsonApplication() {
        register(IndicadorResource.class);
	}
}
