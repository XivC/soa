package ru.ifmo.soa.killer.impl;

import jakarta.ejb.Stateless;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Team;
import ru.ifmo.soa.killer.result.TeamCreateResult;
import ru.ifmo.soa.killer.service.TeamCreator;

@Stateless
public class TeamCreatorImpl implements TeamCreator {

    @Override
    public TeamCreateResult create(Long id, String name, Integer size, Cave cave) {
        return TeamCreateResult
                .builder()
                .team(
                        Team.builder()
                                .size(size)
                                .startCave(cave)
                                .name(name)
                                .id(id)
                                .build()
                )
                .build();
    }
}
