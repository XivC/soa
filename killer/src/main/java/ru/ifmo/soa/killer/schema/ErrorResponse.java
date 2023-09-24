package ru.ifmo.soa.killer.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JacksonXmlRootElement(localName = "Errors")
public class ErrorResponse {
    @JacksonXmlProperty(localName = "Error")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<String> errors;
 }