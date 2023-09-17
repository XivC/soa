package ru.ifmo.soa.service1.dragons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.sql.filter.Filter;
import ru.ifmo.soa.service1.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.service1.app.sql.order.Order;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.app.validation.ValidationError;
import ru.ifmo.soa.service1.dragons.api.schema.CreateOrUpdateDragonRequest;
import ru.ifmo.soa.service1.dragons.api.validation.CreateDragonRequestValidator;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.service.DragonCreator;
import ru.ifmo.soa.service1.dragons.service.DragonDeleter;
import ru.ifmo.soa.service1.dragons.service.DragonGetter;
import ru.ifmo.soa.service1.dragons.service.DragonUpdater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/dragons")
public class DragonResource {

    @Inject
    CreateDragonRequestValidator createDragonRequestValidator;

    @Inject
    DragonCreator dragonCreator;

    @Inject
    DragonGetter dragonGetter;

    @Inject
    DragonUpdater dragonUpdater;

    @Inject
    DragonDeleter dragonDeleter;

    @POST
    @Produces("application/xml")
    public Response createDragon(@QueryParam("dragon") @NotNull final String createDragonRequestString) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();
        try {
            CreateOrUpdateDragonRequest request = mapper.readValue(createDragonRequestString, CreateOrUpdateDragonRequest.class);
            ValidatedData<CreateOrUpdateDragonRequest, CreateDragonRequestValidator> validatedData = new ValidatedData<>(request, createDragonRequestValidator);
            Dragon dragon = dragonCreator.create(validatedData);
            return Response.status(Response.Status.CREATED).entity(dragon).build();

        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ValidationError error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getErrors()).build();
        }


    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response list(
            @QueryParam("filter") String filterString,
            @QueryParam("order") String orderString,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset
    ) throws SQLException, InvalidFilter, ServiceError {

        try {

            List<Filter<String>> filters = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();

            if (filterString != null)
                filters = mapper.readValue(filterString, new TypeReference<>() {
                });

            if (orderString != null)
                orders = mapper.readValue(orderString, new TypeReference<>() {
                });

            List<Dragon> dragons = dragonGetter.getDragons(filters, orders, limit, offset);
            GenericEntity<List<Dragon>> entity = new GenericEntity<>(dragons) {
            };
            return Response.ok().entity(entity).build();


        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ValidationError error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getErrors()).build();
        }
    }


    @GET
    @Path("/{dragonId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getById(@PathParam("dragonId") @NotNull Long dragonId) throws ServiceError {

        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
        if (mbDragon.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(mbDragon.get()).build();

    }

    @PUT
    @Path("/{dragonId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response update(
            @QueryParam("dragon") @NotNull final String createDragonRequestString,
            @PathParam("dragonId") @NotNull Long dragonId
    ) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
            if (mbDragon.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).build();

            CreateOrUpdateDragonRequest request = mapper.readValue(createDragonRequestString, CreateOrUpdateDragonRequest.class);
            ValidatedData<CreateOrUpdateDragonRequest, CreateDragonRequestValidator> validatedData = new ValidatedData<>(request, createDragonRequestValidator);

            Dragon updated = dragonUpdater.update(validatedData, mbDragon.get());

            return Response.ok().entity(updated).build();
        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ValidationError error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getErrors()).build();
        }

    }

    @DELETE
    @Path("/{dragonId}")
    public Response delete(
            @PathParam("dragonId") @NotNull Long dragonId
    ) throws ServiceError {


        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
        if (mbDragon.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();


        dragonDeleter.delete(mbDragon.get());

        return Response.noContent().build();


    }
}