package ru.ifmo.soa.killer.service;

import ru.ifmo.soa.killer.model.Cave;
import ru.ifmo.soa.killer.model.Team;
import ru.ifmo.soa.killer.result.TeamCreateResult;

import javax.ejb.Remote;

@Remote
public interface TeamCreator {

    TeamCreateResult create(Long id, String name, Integer size, Cave cave);
}
