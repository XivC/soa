package ru.ifmo.soa.service1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Coordinates {
    private int x;
    private Long y; //Значение поля должно быть больше -740, Поле не может быть null
}