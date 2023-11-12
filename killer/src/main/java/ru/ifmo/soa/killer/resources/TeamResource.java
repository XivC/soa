package ru.ifmo.soa.killer.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.killer.config.JNDIConfig;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.repository.CaveRepository;
import ru.ifmo.soa.killer.repository.TeamRepository;
import ru.ifmo.soa.killer.result.CaveCreateResult;
import ru.ifmo.soa.killer.result.TeamCreateResult;
import ru.ifmo.soa.killer.schema.ErrorResponse;
import ru.ifmo.soa.killer.service.CaveCreator;
import ru.ifmo.soa.killer.service.TeamCreator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Path("/")
public class TeamResource {

    @Inject
    CaveRepository caveRepository;

    @Inject
    TeamRepository teamRepository;

    @POST
    @Path("teams/create/{teamId}/{teamName}/{teamSize}/{startCaveId}")
    @Produces("application/xml")
    public Response teamsCreate(
            @PathParam("teamId") @NotNull Long teamId,
            @PathParam("teamSize") @NotNull Integer teamSize,
            @PathParam("teamName") @NotNull String teamName,
            @PathParam("startCaveId") @NotNull Long startCaveId
    ) throws JsonProcessingException, SQLException {

        TeamCreator teamCreator = JNDIConfig.teamCreator();
        CaveCreator caveCreator = JNDIConfig.caveCreator();

        if (teamSize <= 0) {
            return Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(List.of("Team size must be > 0")))).build();
        }
        Optional<Cave> mbCave = caveRepository.getById(startCaveId);
        Cave cave;
        if (mbCave.isPresent()) {
            cave = mbCave.get();
        } else {
            CaveCreateResult result = caveCreator.create(startCaveId);
            if (!result.getErrors().isEmpty()) {
                return Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(result.getErrors()))).build();
            }
            cave = result.getCave();
            caveRepository.create(cave);
        }
        TeamCreateResult result = teamCreator.create(teamId, teamName, teamSize, cave);
        if (!result.getErrors().isEmpty()) {
            return Response.status(400).entity(new XmlMapper().writeValueAsString(new ErrorResponse(result.getErrors()))).build();
        }
        teamRepository.create(result.getTeam());
        return Response.status(204).build();
    }
}
