package ru.ifmo.soa.dragons.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ifmo.soa.dragons.model.Color;
import ru.ifmo.soa.dragons.model.Coordinates;
import ru.ifmo.soa.dragons.model.DragonCharacter;
import ru.ifmo.soa.dragons.model.DragonType;

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

}
