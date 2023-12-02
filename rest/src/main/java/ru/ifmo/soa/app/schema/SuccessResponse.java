package ru.ifmo.soa.app.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "messages"
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "SuccessResponse")
public class SuccessResponse {
    @XmlElement(name = "Message")
    List<String> messages;

    public JAXBElement<SuccessResponse> asResponse(){
        return new JAXBElement<>(
                QName.valueOf("SuccessResponse"),
                SuccessResponse.class,
                this
        );
    }
 }