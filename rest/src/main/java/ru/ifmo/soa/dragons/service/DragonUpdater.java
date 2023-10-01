package ru.ifmo.soa.dragons.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.dragons.api.schema.UpdateDragonRequest;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonRepository;
import ru.ifmo.soa.persons.model.Person;
import ru.ifmo.soa.persons.service.PersonGetter;

import java.sql.SQLException;
import java.util.*;

@Service
public class DragonUpdater {

    @Autowired
    DragonRepository dragonRepository;

    @Autowired
    PersonGetter personGetter;

    public Dragon update(ValidatedData<UpdateDragonRequest, ?> validatedData, Dragon dragon) throws ServiceError, ValidationError {

        UpdateDragonRequest data = validatedData.getData();

        if (data.getKillerId() != null){
            Optional<Person> mbPerson = personGetter.getById(data.getKillerId());
            if (!mbPerson.isPresent()) throw new ValidationError(Collections.singletonList(String.format("Killer with passportId %s not found", data.getKillerId())));
            dragon.setKiller(mbPerson.get());
        }
        else {
            dragon.setKiller(null);
        }

        dragon.setAge(data.getAge());
        dragon.setName(data.getName());
        dragon.setColor(data.getColor());
        dragon.setCharacter(data.getCharacter());
        dragon.setCoordinates(data.getCoordinates());
        dragon.setType(data.getType());

        try {
            dragonRepository.save(dragon);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }

        return dragon;


    }


}
