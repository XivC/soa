package ru.ifmo.soa.killer.service;


import jakarta.ejb.Remote;
import ru.ifmo.soa.killer.model.Dragon;
import ru.ifmo.soa.killer.model.Person;
import ru.ifmo.soa.killer.result.DragonKillResult;

@Remote
public interface DragonKiller {

    DragonKillResult kill(Dragon dragon, Person killer);
}