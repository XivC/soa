package ru.ifmo.soa.app.schema;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ErrorResponse {
    List<String> errors;
 }
