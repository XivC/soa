package ru.ifmo.soa.service1.app.sql.filter;

import lombok.Getter;

import java.util.List;

@Getter
public class Filter<T> {

    String key;
    T value;

    public Filter(String key, T value){
        this.key = key;
        this.value = value;
    };

}
