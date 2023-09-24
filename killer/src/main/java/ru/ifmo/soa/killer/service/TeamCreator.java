package ru.ifmo.soa.killer.service;

import jakarta.inject.Inject;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Team;
import ru.ifmo.soa.killer.repository.TeamRepository;
import ru.ifmo.soa.killer.validation.ValidationError;

import java.sql.SQLException;
import java.util.List;

public class TeamCreator {

    @Inject
    TeamRepository teamRepository;

    public Team create(Long id, String name, Integer size, Cave cave) throws ValidationError, ServiceError{

        Team team = Team.builder()
                .size(size)
                .startCave(cave)
                .name(name)
                .id(id)
                .build();

        try {
            if (teamRepository.getById(id).isPresent()) throw new ValidationError(List.of(String.format("Team with id %s already exists", id)));

            teamRepository.create(team);
            return team;

        }
        catch (SQLException ex) {
            throw new ServiceError();
        }




    }


}
