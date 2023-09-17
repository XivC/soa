package ru.ifmo.soa.app.validation;

import java.util.List;

public interface Validator<T> {
    public List<String> validate(T obj);
}
