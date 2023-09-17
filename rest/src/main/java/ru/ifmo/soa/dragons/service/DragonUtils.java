package ru.ifmo.soa.dragons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.dragons.model.DragonType;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;

@Service
public class DragonUtils {

    @Autowired
    DragonRepository dragonRepository;


    public Integer countDragonsByType(DragonType type) throws ServiceError {

        try{
            return dragonRepository.countByType(type);
        }
        catch (SQLException ex){
            throw new ServiceError();
        }

    }

}
