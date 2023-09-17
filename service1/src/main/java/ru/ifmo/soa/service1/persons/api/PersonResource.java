package ru.ifmo.soa.service1.persons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.app.validation.ValidationError;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.service.DragonDeleter;
import ru.ifmo.soa.service1.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.service1.persons.api.schema.UpdatePersonRequest;
import ru.ifmo.soa.service1.persons.api.validation.CreatePersonRequestValidator;
import ru.ifmo.soa.service1.persons.api.validation.UpdatePersonRequestValidator;
import ru.ifmo.soa.service1.persons.model.Person;
import ru.ifmo.soa.service1.persons.service.PersonCreator;
import ru.ifmo.soa.service1.persons.service.PersonDeleter;
import ru.ifmo.soa.service1.persons.service.PersonGetter;
import ru.ifmo.soa.service1.persons.service.PersonUpdater;

import java.util.Optional;

@Path("/persons")
public class PersonResource {

    @Inject
    CreatePersonRequestValidator createPersonRequestValidator;

    @Inject
    UpdatePersonRequestValidator updatePersonRequestValidator;

    @Inject
    PersonCreator personCreator;

    @Inject
    PersonGetter personGetter;

    @Inject
    PersonUpdater personUpdater;

    @Inject
    PersonDeleter personDeleter;

    @POST
    @Produces("application/xml")
    public Response create(@QueryParam("person") @NotNull final String createPersonRequestString) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();
        try {
            CreatePersonRequest request = mapper.readValue(createPersonRequestString, CreatePersonRequest.class);
            ValidatedData<CreatePersonRequest, CreatePersonRequestValidator> validatedData = new ValidatedData<>(request, createPersonRequestValidator);
            Person person = personCreator.create(validatedData);
            return Response.status(Response.Status.CREATED).entity(person).build();

        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ValidationError error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getErrors()).build();
        }


    }


    @GET
    @Path("/{passportId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getById(@PathParam("passportId") @NotNull String passportId) throws ServiceError {

        Optional<Person> mbPerson = personGetter.getById(passportId);
        if (mbPerson.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(mbPerson.get()).build();

    }

    @PUT
    @Path("/{passportId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response update(
            @QueryParam("person") @NotNull final String updatePersonRequestString,
            @PathParam("passportId") @NotNull String passportId
    ) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Optional<Person> mbPerson = personGetter.getById(passportId);
            if (mbPerson.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).build();

            UpdatePersonRequest request = mapper.readValue(updatePersonRequestString, UpdatePersonRequest.class);
            ValidatedData<UpdatePersonRequest, UpdatePersonRequestValidator> validatedData = new ValidatedData<>(request, updatePersonRequestValidator);

            Person updated = personUpdater.update(validatedData, mbPerson.get());

            return Response.ok().entity(updated).build();
        } catch (JsonProcessingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ValidationError error) {
            return Response.status(Response.Status.BAD_REQUEST).entity(error.getErrors()).build();
        }

    }

    @DELETE
    @Path("/{passportId}")
    public Response delete(
            @PathParam("passportId") @NotNull String passportId
    ) throws ServiceError {


        Optional<Person> mbPerson = personGetter.getById(passportId);
        if (mbPerson.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();


        personDeleter.delete(mbPerson.get());

        return Response.noContent().build();


    }
}
