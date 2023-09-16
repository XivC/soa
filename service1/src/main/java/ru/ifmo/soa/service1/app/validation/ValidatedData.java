package ru.ifmo.soa.service1.app.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidatedData <T, V extends Validator<T>>{

    T data;

    public ValidatedData(T data, V validator) throws ValidationError{

        List<String> errors = validator.validate(data);
        if (! errors.isEmpty()) throw new ValidationError(errors);

        this.data = data;

    }

}
