package ru.ifmo.soa.persons.service;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.persons.api.schema.UpdatePersonRequest;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.repository.PersonRepository;

import java.sql.SQLException;

@Service
public class PersonUpdater {


    @Autowired
    PersonRepository personRepository;

    public Person update(ValidatedData<UpdatePersonRequest, ?> validatedData, Person person) throws ServiceError {

        UpdatePersonRequest data = validatedData.getData();

        person.setName(data.getName());
        person.setHeight(data.getHeight());
        person.setWeight(data.getWeight());
        person.setNationality(data.getNationality());

        try {
            personRepository.update(person);
        } catch (SQLException ex) {
            throw new ServiceError();
        }

        return person;


    }
}
