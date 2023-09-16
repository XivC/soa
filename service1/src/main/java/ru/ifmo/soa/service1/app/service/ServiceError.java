package ru.ifmo.soa.service1.app.service;

public class ServiceError extends Exception{
    public ServiceError(){
        super("Internal server error");
    }
}
