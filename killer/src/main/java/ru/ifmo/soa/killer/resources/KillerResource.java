package ru.ifmo.soa.killer.resources;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Dragon;
import ru.ifmo.soa.killer.model.Person;
import ru.ifmo.soa.killer.schema.ErrorResponse;
import ru.ifmo.soa.killer.service.DragonKiller;
import ru.ifmo.soa.killer.validation.ValidationError;

import java.util.Optional;

@Path("/")
public class KillerResource {

    @Inject
    RestServiceClient restServiceClient;

    @Context
    private ContainerRequestContext requestContext;

    @Inject
    DragonKiller dragonKiller;

    @GET
    @Path("dragons/{dragonId}/kill")
    @Produces("application/xml")
    public Response killDragon(@PathParam("dragonId") @NotNull Long dragonId) throws ClientError {

        Optional<Dragon> mbDragon = restServiceClient.getDragonById(dragonId);

        if (mbDragon.isEmpty()){
            return Response.status(404).build();
        }

        Person killer = (Person) requestContext.getProperty("person");

        try{
            Dragon updated = dragonKiller.kill(mbDragon.get(), killer);
            return Response.ok().entity(updated).build();
        }
        catch (ValidationError error){
            return Response.status(400).entity(new ErrorResponse(error.getErrors())).build();
        }


    }



}
