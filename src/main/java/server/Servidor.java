package server;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	public static HttpServer startServer(String baseURI) {
        final ResourceConfig rc = new ResourceConfig().packages("server");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseURI), rc);
    }

}
