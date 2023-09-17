package ru.ifmo.soa.service1.persons.api.validation;

import jakarta.enterprise.context.RequestScoped;
import ru.ifmo.soa.service1.app.validation.Validator;
import ru.ifmo.soa.service1.persons.api.schema.CreatePersonRequest;
import ru.ifmo.soa.service1.persons.api.schema.UpdatePersonRequest;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UpdatePersonRequestValidator implements Validator<UpdatePersonRequest> {
    
    public List<String> validate(UpdatePersonRequest req) {

        List<String> errors = new ArrayList<>();

        if (req.getName() == null || req.getName().isBlank()) errors.add("Name can't be empty");
        if (req.getHeight() != null && req.getHeight() <= 0) errors.add("Height must be greater than 0");
        if (req.getWeight() != null && req.getWeight() <= 0) errors.add("Height must be greater than 0");
        if (req.getNationality() == null) errors.add("Nationality required");

        return errors;

    }
    
}
