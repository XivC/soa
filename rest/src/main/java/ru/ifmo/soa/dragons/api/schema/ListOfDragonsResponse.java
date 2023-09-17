package ru.ifmo.soa.dragons.api.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.ifmo.soa.dragons.model.Dragon;

import java.util.List;

@JacksonXmlRootElement(localName = "Dragons")
public class ListOfDragonsResponse {

    @JacksonXmlProperty(localName = "Dragon")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Dragon> dragons;

    public ListOfDragonsResponse(List<Dragon> dragons){
        this.dragons = dragons;
    }
    public List<Dragon> getDragons() {
        return dragons;
    }
}
