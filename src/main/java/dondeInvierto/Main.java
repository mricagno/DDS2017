package dondeInvierto;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import json.JsonApplication;

public class Main {
	private static final URI BASE_URI = URI.create("http://localhost:8080/dondeInvierto/");
	
	public static void main(String[] args) {
		MercadoBursatil mercado = MercadoBursatil.INSTANCE;	
		mercado.init();
		
        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, new JsonApplication(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
            server.start();

            System.out.println(
            		String.format("Application started.%nTry out %s%nStop the application using CTRL+C", BASE_URI));

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
