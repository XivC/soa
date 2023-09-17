package ru.ifmo.soa.service1.persons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.app.validation.ValidationError;
import ru.ifmo.soa.service1.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.service1.persons.model.Person;
import ru.ifmo.soa.service1.persons.repository.PersonRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class PersonCreator {

    @Inject
    Logger logger;

    @Inject
    PersonRepository personRepository;
    public Person create(ValidatedData<CreatePersonRequest, ?> validatedData) throws ServiceError, ValidationError {

        CreatePersonRequest data = validatedData.getData();
        try {
        Optional<Person> mbPerson = personRepository.getById(data.getPassportID());

        if (mbPerson.isPresent()){
            throw new ValidationError(List.of(String.format("Person with passportID %s already exists", data.getPassportID())));
        }

        Person person = Person.builder()
                .passportID(data.getPassportID())
                .name(data.getName())
                .nationality(data.getNationality())
                .weight(data.getWeight())
                .height(data.getHeight())
                .build();


        personRepository.create(person);
        return person;
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
            throw new ServiceError();
        }




    }

}