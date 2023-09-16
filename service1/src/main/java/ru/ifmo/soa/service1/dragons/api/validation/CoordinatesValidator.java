package ru.ifmo.soa.service1.dragons.api.validation;

import jakarta.enterprise.context.RequestScoped;
import ru.ifmo.soa.service1.app.validation.Validator;
import ru.ifmo.soa.service1.dragons.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CoordinatesValidator implements Validator<Coordinates> {
    
    
    public List<String> validate(Coordinates coordinates) {

        List<String> errors = new ArrayList<>();

        if (coordinates.getY() == null) errors.add("y is required coordinate");
        else if (coordinates.getY() <= -740 ) errors.add("y value must be greater than -740");
        return errors;


    }
    
}
