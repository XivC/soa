package ru.ifmo.soa.service1.dragons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.repository.DragonRepository;

import java.sql.SQLException;

@RequestScoped
public class DragonDeleter {

    @Inject
    DragonRepository dragonRepository;

    public void delete(Dragon dragon) throws ServiceError{
        try {
            dragonRepository.delete(dragon);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }
    }


}
