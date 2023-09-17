package ru.ifmo.soa.persons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.persons.api.schema.UpdatePersonRequest;
import ru.ifmo.soa.persons.api.validation.CreatePersonRequestValidator;
import ru.ifmo.soa.persons.api.validation.UpdatePersonRequestValidator;
import ru.ifmo.soa.persons.service.PersonDeleter;
import ru.ifmo.soa.persons.service.PersonUpdater;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.service.PersonCreator;
import ru.ifmo.soa.persons.service.PersonGetter;

import java.util.Optional;

@RestController("/api/persons")
public class PersonView {

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

    @PostMapping(produces = "application/xml")
    public ResponseEntity<?> create(@RequestParam("person") final String createPersonRequestString) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();
        try {
            CreatePersonRequest request = mapper.readValue(createPersonRequestString, CreatePersonRequest.class);
            ValidatedData<CreatePersonRequest, CreatePersonRequestValidator> validatedData = new ValidatedData<>(request, createPersonRequestValidator);
            Person person = personCreator.create(validatedData);
            return ResponseEntity.status(201).body(person);

        } catch (JsonProcessingException ex) {
            return ResponseEntity.badRequest().build();
        } catch (ValidationError error) {
            return ResponseEntity.badRequest().body(error.getErrors());
        }


    }


    @GetMapping(value = "/{passportId}", produces = "application/xml")
    public ResponseEntity<?> getById(@PathVariable("passportId") final String passportId) throws ServiceError {

        Optional<Person> mbPerson = personGetter.getById(passportId);
        if (mbPerson.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(mbPerson.get());

    }

    @PutMapping(value = "/{passportId}", produces = "application/xml")
    public ResponseEntity<?> update(
            @RequestParam("person") final String updatePersonRequestString,
            @PathVariable("passportId") final String passportId
    ) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Optional<Person> mbPerson = personGetter.getById(passportId);
            if (mbPerson.isEmpty())
                return ResponseEntity.notFound().build();

            UpdatePersonRequest request = mapper.readValue(updatePersonRequestString, UpdatePersonRequest.class);
            ValidatedData<UpdatePersonRequest, UpdatePersonRequestValidator> validatedData = new ValidatedData<>(request, updatePersonRequestValidator);

            Person updated = personUpdater.update(validatedData, mbPerson.get());

            return ResponseEntity.ok().body(updated);
        } catch (JsonProcessingException ex) {
            return ResponseEntity.badRequest().build();
        } catch (ValidationError error) {
            return ResponseEntity.badRequest().body(error.getErrors());
        }

    }

    @DeleteMapping(value = "/{passportId}", produces = "application/xml")
    public ResponseEntity<?> delete(
            @PathVariable("passportId") String passportId
    ) throws ServiceError {


        Optional<Person> mbPerson = personGetter.getById(passportId);
        if (mbPerson.isEmpty())
            return ResponseEntity.notFound().build();


        personDeleter.delete(mbPerson.get());

        return ResponseEntity.noContent().build();


    }
}
