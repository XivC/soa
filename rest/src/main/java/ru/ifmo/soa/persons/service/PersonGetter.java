package ru.ifmo.soa.persons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.repository.PersonRepository;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PersonGetter {

    @Autowired
    PersonRepository personRepository;

    public Optional<Person> getById(String passportId) throws ServiceError {
        try {
            return personRepository.getById(passportId);
        }
        catch (SQLException ex) {
            throw new ServiceError();
        }
    }
}
