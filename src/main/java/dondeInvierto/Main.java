package dondeInvierto;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import json.JsonApplication;

/*import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import dondeInvierto.resource.*;*/

public class Main extends Application {
	private static final URI BASE_URI = URI.create("http://localhost:8080/dondeInvierto/resource/");
	
	public static void main(String[] args) throws Exception {
		MercadoBursatil mercado = MercadoBursatil.INSTANCE;	
		mercado.init();
		mercado.setIntervalo_carga_cuentas(10);
		mercado.setPath_carga_cuentas(".//downloaded//");
		mercado.set_job();

        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new JsonApplication(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            StaticHttpHandler staticHttpHandler = new StaticHttpHandler(".//site//");
            server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/dondeInvierto/");
            server.start();

            System.out.println(
            		String.format("[INFO] (Grizzly) El servidor está en línea.%n[INFO] (Grizzly) Pruebe ingresar"
            				+ " a %s.%n[INFO] (Grizzly) Detenga el servidor usando CTRL+C.", "http://localhost:8080/dondeInvierto/index.html"));
            
            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
//	@Override
//	public Set<Class<?>> getClasses() {
//        final Set<Class<?>> resources = new HashSet<Class<?>>();
//
//        resources.add(CuentaResource.class);
//        resources.add(EmpresaResource.class);
//        resources.add(IndicadorResource.class);
//        resources.add(MetodologiaResource.class);
//        resources.add(UsuarioResource.class);
//
//        resources.add(MultiPartFeature.class);
//
//        return resources;
//    }
}