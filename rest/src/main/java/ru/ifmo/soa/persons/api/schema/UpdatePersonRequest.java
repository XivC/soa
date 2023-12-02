package ru.ifmo.soa.persons.api.schema;

import lombok.*;
import ru.ifmo.soa.persons.model.Country;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name="updatePersonRequest", namespace = "http://ru/ifmo/soa/")
public class UpdatePersonRequest {

    private String name;
    private Long height;
    private Double weight;
    private String passportID;
    private Country nationality;

}