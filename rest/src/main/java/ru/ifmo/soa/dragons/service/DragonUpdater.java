package ru.ifmo.soa.dragons.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.dragons.api.schema.CreateOrUpdateDragonRequest;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;

@Service
public class DragonUpdater {

    @Autowired
    DragonRepository dragonRepository;
    public Dragon update(ValidatedData<CreateOrUpdateDragonRequest, ?> validatedData, Dragon dragon) throws ServiceError {

        CreateOrUpdateDragonRequest data = validatedData.getData();

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
