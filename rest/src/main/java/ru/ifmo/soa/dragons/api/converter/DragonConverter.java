package ru.ifmo.soa.dragons.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.bullshit.ObjectStringifier;
import ru.ifmo.soa.dragons.api.schema.CoordinatesResponse;
import ru.ifmo.soa.dragons.api.schema.DragonResponse;
import ru.ifmo.soa.dragons.api.schema.ListOfDragonsResponse;
import ru.ifmo.soa.dragons.model.Coordinates;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.persons.api.converter.PersonConverter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DragonConverter {

    @Autowired
    CoordinatesConverter coordinatesConverter;

    @Autowired
    PersonConverter personConverter;

    public DragonResponse toResponse(Dragon dragon){
        DragonResponse.DragonResponseBuilder builder =  DragonResponse.builder()
                .id(dragon.getId())
                .coordinates(coordinatesConverter.toResponse(dragon.getCoordinates()))
                .age(dragon.getAge())
                .color(ObjectStringifier.perform(dragon.getColor()))
                .creationDate(dragon.getCreationDate())
                .name(dragon.getName())
                .type(ObjectStringifier.perform(dragon.getType()))
                .character(ObjectStringifier.perform(dragon.getCharacter()));

        if (dragon.getKiller() != null){
            builder.killer(personConverter.toResponse(dragon.getKiller()));
        }
        return builder.build();
    }

    public ListOfDragonsResponse toListResponse(List<Dragon> dragons){
        return new ListOfDragonsResponse(
                dragons.stream().map(this::toResponse).collect(Collectors.toList())
        );
    }

}
