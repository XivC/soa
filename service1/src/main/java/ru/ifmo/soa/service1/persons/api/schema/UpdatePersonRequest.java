package ru.ifmo.soa.service1.persons.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ifmo.soa.service1.persons.model.Country;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePersonRequest {

    private String name;
    private Long height;
    private Double weight;
    private Country nationality;

}
