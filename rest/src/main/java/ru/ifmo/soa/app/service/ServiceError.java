package ru.ifmo.soa.app.service;

public class ServiceError extends Exception{
    public ServiceError(){
        super("Internal server error");
    }
}
