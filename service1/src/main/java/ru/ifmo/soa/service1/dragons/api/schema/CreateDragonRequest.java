package ru.ifmo.soa.service1.dragons.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ifmo.soa.service1.dragons.model.Color;
import ru.ifmo.soa.service1.dragons.model.Coordinates;
import ru.ifmo.soa.service1.dragons.model.DragonCharacter;
import ru.ifmo.soa.service1.dragons.model.DragonType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDragonRequest{

    String name;
    Coordinates coordinates;
    Integer age;
    Color color;
    DragonType type;
    DragonCharacter character;

}
