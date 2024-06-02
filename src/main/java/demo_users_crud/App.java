package demo_users_crud;

import org.glassfish.grizzly.http.server.HttpServer;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;

import java.io.IOException;
import java.net.URI;

public class App {
    public static void main(String[] args) throws IOException{
        ResourceConfig rc = new PackagesResourceConfig("demo_users_crud");
        URI uri = URI.create("http://localhost:8080/");
        HttpServer server = GrizzlyServerFactory.createHttpServer(uri, rc);
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
