package ru.ifmo.soa.service1.app.validation;

import java.util.List;

public interface Validator<T> {
    public List<String> validate(T obj);
}
