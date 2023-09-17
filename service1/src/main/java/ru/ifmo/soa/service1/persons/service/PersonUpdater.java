package ru.ifmo.soa.service1.persons.service;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.service1.persons.api.schema.UpdatePersonRequest;
import ru.ifmo.soa.service1.persons.model.Person;
import ru.ifmo.soa.service1.persons.repository.PersonRepository;

import java.sql.SQLException;

public class PersonUpdater {


    @Inject
    PersonRepository personRepository;

    @Inject
    Logger logger;

    public Person update(ValidatedData<UpdatePersonRequest, ?> validatedData, Person person) throws ServiceError {

        UpdatePersonRequest data = validatedData.getData();

        person.setName(data.getName());
        person.setHeight(data.getHeight());
        person.setWeight(data.getWeight());
        person.setNationality(data.getNationality());

        try {
            personRepository.update(person);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new ServiceError();
        }

        return person;


    }
}
