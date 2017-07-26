package dondeInvierto;

import java.io.IOException;
import java.text.ParseException;

import org.glassfish.grizzly.http.server.HttpServer;

import server.Servidor;

public class Main {
	public static final String BASE_URI = "http://localhost:8080/dondeInvierto/";
	
	public static void main(String[] args) throws IOException, ParseException {
		MercadoBursatil mercado = MercadoBursatil.INSTANCE;	
		mercado.init();
		
        final HttpServer server = Servidor.startServer(BASE_URI);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
	}

}
