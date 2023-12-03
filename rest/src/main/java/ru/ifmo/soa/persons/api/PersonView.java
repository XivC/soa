package ru.ifmo.soa.persons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.ifmo.soa.app.schema.ErrorResponse;
import ru.ifmo.soa.app.schema.SuccessResponse;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.dragons.api.schema.ListOfDragonsResponse;
import ru.ifmo.soa.persons.api.converter.PersonConverter;
import ru.ifmo.soa.persons.api.schema.*;
import ru.ifmo.soa.persons.api.validation.CreatePersonRequestValidator;
import ru.ifmo.soa.persons.api.validation.UpdatePersonRequestValidator;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.service.PersonCreator;
import ru.ifmo.soa.persons.service.PersonDeleter;
import ru.ifmo.soa.persons.service.PersonGetter;
import ru.ifmo.soa.persons.service.PersonUpdater;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Optional;

@Endpoint
public class PersonView {

    private static final String NAMESPACE_URI = "http://ru/ifmo/soa/";

    @Autowired
    PersonConverter personConverter;

    @Autowired
    CreatePersonRequestValidator createPersonRequestValidator;

    @Autowired
    UpdatePersonRequestValidator updatePersonRequestValidator;

    @Autowired
    PersonCreator personCreator;

    @Autowired
    PersonGetter personGetter;

    @Autowired
    PersonUpdater personUpdater;

    @Autowired
    PersonDeleter personDeleter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPersonRequest")
    @ResponsePayload
    public JAXBElement<?> create(@RequestPayload CreatePersonRequest request) throws ServiceError {


        try {
            ValidatedData<CreatePersonRequest, CreatePersonRequestValidator> validatedData = new ValidatedData<>(request, createPersonRequestValidator);
            Person person = personCreator.create(validatedData);
            return new JAXBElement<>(
                    QName.valueOf("personResponse"),
                    PersonResponse.class,
                    personConverter.toResponse(person)
            );

        }  catch (ValidationError error) {
            return new ErrorResponse(error.getErrors()).asResponse();
        }


    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonRequest")
    @ResponsePayload
    public JAXBElement<?> getById(@RequestPayload GetPersonRequest request) throws ServiceError {

        Optional<Person> mbPerson = personGetter.getById(request.getPassportID());
        if (!mbPerson.isPresent()) return new ErrorResponse(List.of("Not found")).asResponse();
        return new JAXBElement<>(
                QName.valueOf("personResponse"),
                PersonResponse.class,
                personConverter.toResponse(mbPerson.get())
        );

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePersonRequest")
    @ResponsePayload
    public JAXBElement<?> update(
            @RequestPayload UpdatePersonRequest request
    ) throws ServiceError {


        try {
            Optional<Person> mbPerson = personGetter.getById(request.getPassportID());
            if (!mbPerson.isPresent()) return new ErrorResponse(List.of("Not found")).asResponse();


            ValidatedData<UpdatePersonRequest, UpdatePersonRequestValidator> validatedData = new ValidatedData<>(request, updatePersonRequestValidator);

            Person updated = personUpdater.update(validatedData, mbPerson.get());

            return new JAXBElement<>(
                    QName.valueOf("personResponse"),
                    PersonResponse.class,
                    personConverter.toResponse(updated)
            );
        } catch (ValidationError error) {
             return new ErrorResponse(error.getErrors()).asResponse();
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonRequest")
    @ResponsePayload
    public JAXBElement<?> delete(
            @RequestPayload DeletePersonRequest request
            ) throws ServiceError {


        Optional<Person> mbPerson = personGetter.getById(request.getPassportID());
        if (!mbPerson.isPresent())
            return new ErrorResponse(List.of("Not found")).asResponse();


        personDeleter.delete(mbPerson.get());

        return new SuccessResponse(List.of("Deleted")).asResponse();


    }
}
