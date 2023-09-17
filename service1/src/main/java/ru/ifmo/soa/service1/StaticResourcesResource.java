package ru.ifmo.soa.service1;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Path("static")
public class StaticResourcesResource {

    @Inject
    ServletContext context;

    private String convertToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @GET
    @Path("{path:.*}")
    public Response staticResources(@PathParam("path") final String path) throws IOException {

        InputStream resource = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("WEB-INF/" + path);

        return Objects.isNull(resource)
                ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok().entity(convertToString(resource)).build();
    }
}