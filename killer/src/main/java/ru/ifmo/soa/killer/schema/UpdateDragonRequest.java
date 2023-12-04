package ru.ifmo.soa.killer.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ifmo.soa.killer.model.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateDragonRequest {

    String name;
    Coordinates coordinates;
    Integer age;
    Color color;
    DragonType type;
    DragonCharacter character;
    String killerId;

    public static UpdateDragonRequest fromDragon(Dragon dragon){
        return new UpdateDragonRequest(
                dragon.getName(),
                dragon.getCoordinates(),
                dragon.getAge(),
                dragon.getColor(),
                dragon.getType(),
                dragon.getCharacter(),
                dragon.getKillerId()
        );
    }

}
