//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.12.02 at 11:08:57 PM MSK 
//


package ru.ifmo.soa.dragons.api.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "filter",
    "order",
    "limit",
    "offset"
})
@Getter
@Setter
@XmlRootElement(name = "getDragonsRequest", namespace = "http://ru/ifmo/soa/")
public class GetDragonsRequest {

    @XmlElement(name = "filter", required = true, nillable = false)
    protected String filter;
    @XmlElement(required = false, nillable = true)
    protected String order;
    @XmlElement(required = false, type = Integer.class, nillable = true)
    protected Integer limit;
    @XmlElement(required = false, type = Integer.class, nillable = true)
    protected Integer offset;

}
