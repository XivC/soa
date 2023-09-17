package ru.ifmo.soa.dragons.api.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.validation.Validator;
import ru.ifmo.soa.dragons.api.schema.CreateDragonRequest;
import ru.ifmo.soa.dragons.api.schema.UpdateDragonRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateDragonRequestValidator implements Validator<UpdateDragonRequest> {

    @Autowired
    CoordinatesValidator coordinatesValidator;
    
    public List<String> validate(UpdateDragonRequest req) {

        List<String> errors = new ArrayList<>();

        if (req.getName() == null || req.getName().isBlank()) errors.add("Name can't be empty");
        if (req.getCoordinates() == null) errors.add("Coordinates required");
            else  errors.addAll(coordinatesValidator.validate(req.getCoordinates()));
        if (req.getAge() != null && req.getAge() <= 0) errors.add("Age must be greater than 0");
        if (req.getCharacter() == null) errors.add("Dragon character is required");

        return errors;

    }
    
}
