package ru.ifmo.soa.killer.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Dragon;
import ru.ifmo.soa.killer.model.Person;
import ru.ifmo.soa.killer.model.Team;
import ru.ifmo.soa.killer.schema.ErrorResponse;
import ru.ifmo.soa.killer.service.CaveService;
import ru.ifmo.soa.killer.service.DragonKiller;
import ru.ifmo.soa.killer.service.ServiceError;
import ru.ifmo.soa.killer.service.TeamCreator;
import ru.ifmo.soa.killer.validation.ValidationError;

import java.util.List;
import java.util.Optional;

@Path("/")
public class TeamResource {

    @Inject
    TeamCreator teamCreator;

    @Inject
    CaveService caveService;

    @POST
    @Path("teams/create/{teamId}/{teamName}/{teamSize}/{startCaveId}")
    @Produces("application/xml")
    public Response teamsCreate(
            @PathParam("teamId") @NotNull Long teamId,
            @PathParam("teamSize") @NotNull Integer teamSize,
            @PathParam("teamName") @NotNull String teamName,
            @PathParam("startCaveId") @NotNull Long startCaveId
    ) throws ServiceError, JsonProcessingException {

        if (teamSize <= 0) {
            Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(List.of("Team size must be > 0")))).build();  // I am lazy =(
        }


        Cave cave = caveService.getOrCreate(startCaveId);

        try {

            teamCreator.create(teamId, teamName, teamSize, cave);

            return Response.status(204).build();


        } catch (ValidationError error) {
            return Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(error.getErrors()))).build();
        }
    }


}
