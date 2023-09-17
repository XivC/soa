package ru.ifmo.soa.service1.app.bullshit;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class MimeTypeConfiguration implements
        ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.endsWith(".js")) {
            responseContext.getHeaders().putSingle("Content-Type", "text/javascript");
        } else if (path.endsWith(".html")) {
            responseContext.getHeaders().putSingle("Content-Type", "text/html");
        }
    }
}