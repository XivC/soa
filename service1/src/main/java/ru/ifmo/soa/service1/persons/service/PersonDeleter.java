package ru.ifmo.soa.service1.persons.service;

import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.repository.DragonRepository;
import ru.ifmo.soa.service1.persons.model.Person;
import ru.ifmo.soa.service1.persons.repository.PersonRepository;

import java.sql.SQLException;

public class PersonDeleter {

    @Inject
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
