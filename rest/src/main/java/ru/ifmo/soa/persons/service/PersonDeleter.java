package ru.ifmo.soa.persons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.repository.PersonRepository;

import java.sql.SQLException;

@Service
public class PersonDeleter {

    @Autowired
    PersonRepository personRepository;

    public void delete(Person person) throws ServiceError {
        try {
            personRepository.delete(person);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }
    }

}
