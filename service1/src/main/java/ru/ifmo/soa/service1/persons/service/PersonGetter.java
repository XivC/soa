package ru.ifmo.soa.service1.persons.service;

import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.persons.model.Person;
import ru.ifmo.soa.service1.persons.repository.PersonRepository;

import java.sql.SQLException;
import java.util.Optional;

public class PersonGetter {

    @Inject
    PersonRepository personRepository;

    public Optional<Person> getById(String passportId) throws ServiceError{
        try {
            return personRepository.getById(passportId);
        }
        catch (SQLException ex) {
            throw new ServiceError();
        }
    }
}
