package ru.ifmo.soa.dragons.service;

;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.dragons.api.schema.CreateDragonRequest;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;

@Service
public class DragonCreator {



    @Autowired
    DragonRepository dragonRepository;
    public Dragon create(ValidatedData<CreateDragonRequest, ?> validatedData) throws ServiceError {

        CreateDragonRequest data = validatedData.getData();

        Dragon dragon = Dragon.builder()
                .name(data.getName())
                .character(data.getCharacter())
                .type(data.getType())
                .coordinates(data.getCoordinates())
                .color(data.getColor())
                .character(data.getCharacter())
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
