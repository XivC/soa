package ru.ifmo.soa.persons.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "Person")
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long height; //Значение поля должно быть больше 0
    private double weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 47, Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле не может быть null
    private Country nationality; //Поле не может быть null
}