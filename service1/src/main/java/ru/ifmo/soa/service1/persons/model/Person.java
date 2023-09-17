package ru.ifmo.soa.service1.persons.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.soa.service1.persons.model.Country;

@Getter
@Setter
@Builder
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long height; //Значение поля должно быть больше 0
    private double weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 47, Значение этого поля должно быть уникальным, Строка не может быть пустой, Поле не может быть null
    private Country nationality; //Поле не может быть null
}