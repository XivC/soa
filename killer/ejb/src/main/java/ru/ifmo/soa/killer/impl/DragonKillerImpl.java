package ru.ifmo.soa.killer.impl;

import jakarta.ejb.Stateless;
import ru.ifmo.soa.killer.model.Dragon;
import ru.ifmo.soa.killer.model.Person;
import ru.ifmo.soa.killer.result.DragonKillResult;
import ru.ifmo.soa.killer.service.DragonKiller;

import java.util.List;
import java.util.Optional;

@Stateless
public class DragonKillerImpl implements DragonKiller {

    @Override
    public DragonKillResult kill(Dragon dragon, Person killer) {
        DragonKillResult result = new DragonKillResult();
        if (Optional.ofNullable(dragon.getKiller()).map(p -> !p.getPassportID().isEmpty()).orElse(false)) {
            result.setErrors(List.of(String.format("Dragon %s already killed", dragon.getId())));
        } else {
            dragon.setKiller(killer);
            result.setDragon(dragon);
        }
        return result;
    }
}
