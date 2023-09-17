package ru.ifmo.soa.persons.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.repository.PersonRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonCreator {


    @Autowired
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
            throw new ServiceError();
        }




    }

}