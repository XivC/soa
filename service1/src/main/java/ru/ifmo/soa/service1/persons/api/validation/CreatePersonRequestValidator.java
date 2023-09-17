package ru.ifmo.soa.service1.persons.api.validation;

import jakarta.enterprise.context.RequestScoped;
import ru.ifmo.soa.service1.app.validation.Validator;
import ru.ifmo.soa.service1.persons.api.schema.CreatePersonRequest;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class CreatePersonRequestValidator implements Validator<CreatePersonRequest> {
    
    public List<String> validate(CreatePersonRequest req) {

        List<String> errors = new ArrayList<>();

        String passportID = req.getPassportID();

        if (passportID == null || passportID.isBlank()) errors.add("PassportID required");
        if (passportID != null && passportID.length() > 47) errors.add("PassportID length cant be greater than 47");

        if (req.getName() == null || req.getName().isBlank()) errors.add("Name can't be empty");
        if (req.getHeight() != null && req.getHeight() <= 0) errors.add("Height must be greater than 0");
        if (req.getWeight() != null && req.getWeight() <= 0) errors.add("Height must be greater than 0");
        if (req.getNationality() == null) errors.add("Nationality required");

        return errors;

    }
    
}
