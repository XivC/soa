package ru.ifmo.soa.app.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationError extends Exception{
    List<String> errors;

}
