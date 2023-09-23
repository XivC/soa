package ru.ifmo.soa.killer.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    private int x;
    private Long y; //Значение поля должно быть больше -740, Поле не может быть null
}