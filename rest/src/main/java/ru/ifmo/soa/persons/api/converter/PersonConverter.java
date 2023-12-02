package ru.ifmo.soa.persons.api.converter;

import org.springframework.stereotype.Service;
import ru.ifmo.soa.persons.api.schema.PersonResponse;
import ru.ifmo.soa.persons.model.Person;

@Service
public class PersonConverter {



    public PersonResponse toResponse(Person person){
        return PersonResponse.builder()
                .nationality(person.getNationality())
                .passportID(person.getPassportID())
                .weight(person.getWeight())
                .height(person.getHeight())
                .name(person.getName())
                .build();
    }


}
