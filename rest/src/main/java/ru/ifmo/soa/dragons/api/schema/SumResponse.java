package ru.ifmo.soa.dragons.api.schema;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.ifmo.soa.dragons.model.Dragon;

import java.util.List;

@JacksonXmlRootElement(localName = "Sum")
public class SumResponse {

    @JacksonXmlElementWrapper(useWrapping = false)
    private final Integer sum;

    public SumResponse(Integer sum){
        this.sum = sum;
    }
    public Integer getSum() {
        return sum;
    }
}
