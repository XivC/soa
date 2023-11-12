package ru.ifmo.soa.killer.impl;

import jakarta.ejb.Stateless;
import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Team;
import ru.ifmo.soa.killer.result.CaveCreateResult;
import ru.ifmo.soa.killer.result.TeamCreateResult;
import ru.ifmo.soa.killer.service.CaveCreator;
import ru.ifmo.soa.killer.service.TeamCreator;

@Stateless
public class CaveCreatorImpl implements CaveCreator {

    @Override
    public CaveCreateResult create(Long id) {
        return CaveCreateResult
                .builder()
                .cave(
                        Cave.builder()
                                .id(id)
                                .build()
                )
                .build();
    }
}
