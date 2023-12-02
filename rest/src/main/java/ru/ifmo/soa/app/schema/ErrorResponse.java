package ru.ifmo.soa.app.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ifmo.soa.dragons.api.schema.ListOfDragonsResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "errors"
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ErrorResponse")
public class ErrorResponse {
    @XmlElement(name = "Error")
    List<String> errors;

    public JAXBElement<ErrorResponse> asResponse(){
        return new JAXBElement<>(
                QName.valueOf("ErrorResponse"),
                ErrorResponse.class,
                this
        );
    }
 }