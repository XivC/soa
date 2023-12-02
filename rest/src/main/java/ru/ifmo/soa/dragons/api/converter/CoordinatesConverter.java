package ru.ifmo.soa.dragons.api.converter;

import org.springframework.stereotype.Service;
import ru.ifmo.soa.dragons.api.schema.CoordinatesResponse;
import ru.ifmo.soa.dragons.model.Coordinates;

@Service
public class CoordinatesConverter {

    public CoordinatesResponse toResponse(Coordinates coordinates){
        return CoordinatesResponse.builder()
                .x(coordinates.getX())
                .y(coordinates.getY())
                .build();
    }

}
