package ru.ifmo.soa.killer.resources;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Dragon;

@Path("/killer")
public class KillerResource {

    @Inject
    RestServiceClient restServiceClient;

    @GET
    @Path("/{dragonId}/kill")
    public Dragon killDragon(@PathParam("dragonId") @NotNull Long dragonId) throws ClientError {

        Dragon dragon = restServiceClient.getDragonById(dragonId);
        return dragon;



    }

}
