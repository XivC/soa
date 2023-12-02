package ru.ifmo.soa.dragons.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ifmo.soa.dragons.model.Color;
import ru.ifmo.soa.dragons.model.Coordinates;
import ru.ifmo.soa.dragons.model.DragonCharacter;
import ru.ifmo.soa.dragons.model.DragonType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name="updateDragonRequest", namespace = "http://ru/ifmo/soa/")
public class UpdateDragonRequest {

    Long id;
    String name;
    CoordinatesRequest coordinates;
    Integer age;
    Color color;
    DragonType type;
    DragonCharacter character;
    String killerId;

}
