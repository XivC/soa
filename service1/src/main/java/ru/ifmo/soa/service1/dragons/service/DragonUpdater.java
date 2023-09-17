package ru.ifmo.soa.service1.dragons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.sql.filter.Filter;
import ru.ifmo.soa.service1.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.service1.app.sql.order.InvalidOrder;
import ru.ifmo.soa.service1.app.sql.order.Order;
import ru.ifmo.soa.service1.app.validation.ValidatedData;
import ru.ifmo.soa.service1.app.validation.ValidationError;
import ru.ifmo.soa.service1.dragons.api.schema.CreateOrUpdateDragonRequest;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.repository.DragonFilterSet;
import ru.ifmo.soa.service1.dragons.repository.DragonOrderSet;
import ru.ifmo.soa.service1.dragons.repository.DragonRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class DragonUpdater {
    @Inject
    Logger logger;

    @Inject
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
            logger.error(ex.getMessage());
            throw new ServiceError();
        }

        return dragon;


    }


}
