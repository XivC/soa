package ru.ifmo.soa.killer.service;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.killer.client.ClientError;
import ru.ifmo.soa.killer.client.RestServiceClient;
import ru.ifmo.soa.killer.model.Dragon;
import ru.ifmo.soa.killer.model.Person;
import ru.ifmo.soa.killer.validation.ValidationError;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestScoped
public class DragonKiller {

    @Inject
    RestServiceClient client;

    public Dragon kill(Dragon dragon, Person killer) throws ValidationError, ClientError {

        if (Optional.ofNullable(dragon.getKillerId()).isPresent()) throw new ValidationError(List.of(String.format("Dragon %s already killed", dragon.getId())));

        dragon.setKillerId(killer.getPassportID());
        Optional<Dragon> mbUpdatedDragon = client.update(dragon);

        if (mbUpdatedDragon.isEmpty()) throw new ValidationError(List.of(String.format("Ooopsss looks like dragon %s not found", dragon.getId())));

        return mbUpdatedDragon.get();

    }

}
