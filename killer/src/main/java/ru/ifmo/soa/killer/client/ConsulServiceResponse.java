package ru.ifmo.soa.killer.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsulServiceResponse {
    @JsonProperty("Port")
    Integer port;

    @JsonProperty("Address")
    String address;

}
