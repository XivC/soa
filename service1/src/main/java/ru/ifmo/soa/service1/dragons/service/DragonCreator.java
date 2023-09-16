package ru.ifmo.soa.service1.dragons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.dragons.api.schema.CreateDragonRequest;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.repository.DragonRepository;

import java.sql.SQLException;

@RequestScoped
public class DragonCreator {

    @Inject
    Logger logger;

    @Inject
    DragonRepository dragonRepository;
    public Dragon create(ValidatedData<CreateDragonRequest, ?> validatedData) throws ServiceError {

        CreateDragonRequest data = validatedData.getData();

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
            logger.error(ex.getMessage());
            throw new ServiceError();
        }

        return dragon;


    }

}
