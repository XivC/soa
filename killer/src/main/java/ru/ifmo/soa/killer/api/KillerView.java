package ru.ifmo.soa.killer.api;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Dragon;

@Path("/")
public class KillerView {

    @Inject
    RestServiceClient client;

    @GET
    @Path("{dragonId}/kill")
    public Response kill(@PathParam("dragonId") @NotNull Long dragonId) throws ClientError {

        Dragon dragon = client.getDragonById(dragonId);

        return Response.ok().entity(dragon).build();
    }



}
