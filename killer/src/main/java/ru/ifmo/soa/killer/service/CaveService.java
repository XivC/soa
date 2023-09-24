package ru.ifmo.soa.killer.service;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.repository.CaveRepository;

import java.sql.SQLException;
import java.util.Optional;

@RequestScoped
public class CaveService {

    @Inject
    CaveRepository caveRepository;


    public Cave getOrCreate(Long id) throws ServiceError {

        try {
            Optional<Cave> mbCave = caveRepository.getById(id);
            if (mbCave.isPresent()) return mbCave.get();

            Cave cave = new Cave(id);
            caveRepository.create(cave);

            return cave;
        }
        catch (SQLException ex){
            throw new ServiceError();
        }

    }

}
