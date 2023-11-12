package ru.ifmo.soa.killer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationError extends Exception implements Serializable {
    List<String> errors;

}
