package ru.ifmo.soa.killer.model;

public class ServiceError extends Exception{
    public ServiceError(){
        super("Internal server error");
    }
}
