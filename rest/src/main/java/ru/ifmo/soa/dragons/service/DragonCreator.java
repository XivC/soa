package ru.ifmo.soa.dragons.service;

;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.dragons.api.schema.CreateOrUpdateDragonRequest;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;

@Service
public class DragonCreator {



    @Autowired
    DragonRepository dragonRepository;
    public Dragon create(ValidatedData<CreateOrUpdateDragonRequest, ?> validatedData) throws ServiceError {

        CreateOrUpdateDragonRequest data = validatedData.getData();

        Dragon dragon = Dragon.builder()
                .name(data.getName())
                .character(data.getCharacter())
                .type(data.getType())
                .age(data.getAge())
                .build();

        try {
            dragonRepository.save(dragon);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }

        return dragon;


    }

}
