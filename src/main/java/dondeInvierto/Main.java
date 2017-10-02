package dondeInvierto;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import json.JsonApplication;

public class Main {
	private static final URI BASE_URI = URI.create("http://localhost:8080/dondeInvierto/");
	
	public static void main(String[] args) throws ParseException {
		MercadoBursatil mercado = MercadoBursatil.INSTANCE;	
		mercado.init();
		
        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new JsonApplication(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            StaticHttpHandler staticHttpHandler = new StaticHttpHandler(".//site//");
            server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");
            server.start();

            System.out.println(
            		String.format("[INFO] (Grizzly) El servidor está en línea.%n[INFO] (Grizzly) Pruebe ingresar"
            				+ " a %s.%n[INFO] (Grizzly) Detenga el servidor usando CTRL+C.", "http://localhost:8080/index.html"));
            
            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}