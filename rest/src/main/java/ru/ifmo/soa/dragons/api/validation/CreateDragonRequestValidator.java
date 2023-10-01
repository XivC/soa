package ru.ifmo.soa.dragons.api.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.validation.Validator;
import ru.ifmo.soa.dragons.api.schema.CreateDragonRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateDragonRequestValidator implements Validator<CreateDragonRequest> {

    @Autowired
    CoordinatesValidator coordinatesValidator;
    
    public List<String> validate(CreateDragonRequest req) {

        List<String> errors = new ArrayList<>();

        if (req.getName() == null || req.getName().trim().isEmpty()) errors.add("Name can't be empty");
        if (req.getCoordinates() == null) errors.add("Coordinates required");
            else  errors.addAll(coordinatesValidator.validate(req.getCoordinates()));
        if (req.getAge() != null && req.getAge() <= 0) errors.add("Age must be greater than 0");

        return errors;

    }
    
}
