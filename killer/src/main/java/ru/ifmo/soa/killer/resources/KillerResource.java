package ru.ifmo.soa.killer.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.config.JNDIConfig;
import ru.ifmo.soa.killer.model.*;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.result.DragonKillResult;
import ru.ifmo.soa.killer.schema.ErrorResponse;
import ru.ifmo.soa.killer.service.DragonKiller;

import java.util.Optional;

@Path("/")
public class KillerResource {

    @Inject
    RestServiceClient restServiceClient;

    @Context
    private ContainerRequestContext requestContext;


    @POST
    @Path("dragons/{dragonId}/kill")
    @Produces("application/xml")
    public Response killDragon(@PathParam("dragonId") @NotNull Long dragonId) throws ClientError, JsonProcessingException {
        DragonKiller dragonKiller = JNDIConfig.dragonKiller();

        Optional<Dragon> mbDragon = restServiceClient.getDragonById(dragonId);

        if (mbDragon.isEmpty()) {
            return Response.status(404).build();
        }

        Person killer = (Person) requestContext.getProperty("person");
        DragonKillResult result = dragonKiller.kill(mbDragon.get(), killer);
        if (result.getErrors().isEmpty()) {
            restServiceClient.update(result.getDragon());
            return Response.status(204).build();
        } else {
            return Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(result.getErrors()))).build();
        }
    }
}
