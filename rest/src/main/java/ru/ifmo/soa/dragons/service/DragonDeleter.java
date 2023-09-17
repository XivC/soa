package ru.ifmo.soa.dragons.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;

@Service
public class DragonDeleter {
    @Autowired
    DragonRepository dragonRepository;

    public void delete(Dragon dragon) throws ServiceError {
        try {
            dragonRepository.delete(dragon);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }
    }


}
