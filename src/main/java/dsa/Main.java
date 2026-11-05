package dsa;

import io.swagger.jaxrs.config.BeanConfig;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    //Rest
    public static final String BASE_URI = "http://localhost:8080/dsaApp/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("dsa.services");
        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/dsaApp");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setTitle("Flota de drones - DSA");
        beanConfig.setDescription("Gestion de una flota de drones");
        beanConfig.setVersion("1.0.0");
        beanConfig.setResourcePackage("dsa.services");
        beanConfig.setScan(true);

        //servidor Grizzly
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        //http://localhost:8080/swagger/
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("./public/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        logger.info("Servicio arrancado en " + BASE_URI);
        logger.info("Swagger en http://localhost:8080/swagger/");
        logger.info("Pulsa ENTER para parar el servidor...");
        System.in.read();
        server.shutdownNow();
    }
}