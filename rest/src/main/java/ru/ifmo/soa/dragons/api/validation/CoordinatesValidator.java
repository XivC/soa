package ru.ifmo.soa.dragons.api.validation;


import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.validation.Validator;
import ru.ifmo.soa.dragons.api.schema.CoordinatesRequest;
import ru.ifmo.soa.dragons.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatesValidator implements Validator<CoordinatesRequest> {
    
    
    public List<String> validate(CoordinatesRequest coordinates) {

        List<String> errors = new ArrayList<>();

        if (coordinates.getY() == null) errors.add("y is required coordinate");
        else if (coordinates.getY() <= -740 ) errors.add("y value must be greater than -740");
        return errors;


    }
    
}
