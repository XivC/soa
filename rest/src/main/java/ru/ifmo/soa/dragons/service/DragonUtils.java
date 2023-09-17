package ru.ifmo.soa.dragons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.dragons.model.Color;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.model.DragonType;
import ru.ifmo.soa.dragons.repository.DragonFilterSet;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;
import java.util.List;

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

    public List<Dragon> filterByColor (Color color, Integer limit, Integer offset) throws ServiceError {

        try{
            DragonFilterSet filterSet = new DragonFilterSet();
            filterSet.add(new Filter<>("color", color));
            return dragonRepository.getList(filterSet, null, limit, offset);
        }
        catch (SQLException | InvalidFilter ex){
            throw new ServiceError();
        }

    }

    public Integer sumAge() throws ServiceError{
        try {
            return dragonRepository.sumAge();
        }
        catch (SQLException ex){
            throw new ServiceError();
        }
    }
}
