package ru.ifmo.soa.killer.service;

public class ServiceError extends Exception{
    public ServiceError(){
        super("Internal server error");
    }
}
