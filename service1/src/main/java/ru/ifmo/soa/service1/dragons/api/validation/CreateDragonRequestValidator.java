package ru.ifmo.soa.service1.dragons.api.validation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.service1.dragons.api.schema.CreateDragonRequest;
import ru.ifmo.soa.service1.app.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CreateDragonRequestValidator implements Validator<CreateDragonRequest> {

    @Inject
    CoordinatesValidator coordinatesValidator;
    
    public List<String> validate(CreateDragonRequest req) {

        List<String> errors = new ArrayList<>();

        if (req.getName() == null || req.getName().isBlank()) errors.add("Name can't be empty");
        if (req.getCoordinates() == null) errors.add("Coordinates required");
            else  errors.addAll(coordinatesValidator.validate(req.getCoordinates()));
        if (req.getAge() != null && req.getAge() <= 0) errors.add("Age must be greater than 0");
        if (req.getCharacter() == null) errors.add("Dragon character is required");

        return errors;

    }
    
}
