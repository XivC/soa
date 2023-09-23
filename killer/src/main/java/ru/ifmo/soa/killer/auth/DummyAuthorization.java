package ru.ifmo.soa.killer.auth;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Person;

import java.io.IOException;

@WebFilter
public class DummyAuthorization implements ContainerRequestFilter {

    @Inject
    RestServiceClient restServiceClient;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        String passportId = (String) containerRequestContext.getProperty("Authorization");
        try {
            Person person = restServiceClient.getPersonById(passportId);

        }
        catch (ClientError ex){
            containerRequestContext.abortWith(Response.serverError().build());
        }


    }
}
