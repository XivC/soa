package ru.ifmo.soa.killer.auth;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Person;

import java.io.IOException;
import java.util.Optional;

@Provider
@PreMatching
public class DummyAuthorization implements ContainerRequestFilter {

    @Inject
    RestServiceClient restServiceClient;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        String passportId = containerRequestContext.getHeaders().get("authorization").get(0);
        try {
            if (passportId != null) {
                Optional<Person> mbPerson = restServiceClient.getPersonById(passportId);
                if (mbPerson.isEmpty()) {
                    containerRequestContext.abortWith(Response.status(401).build());
                    return;
                }
                containerRequestContext.setProperty("person", mbPerson.get());
            } else {
                containerRequestContext.abortWith(Response.status(401).build());
            }

        }
        catch (ClientError ex){
            containerRequestContext.abortWith(Response.serverError().build());
        }


    }
}
