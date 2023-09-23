package ru.ifmo.soa.app.sql.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Filter<T> {

    String key;
    T value;

    public Filter(String key, T value){
        this.key = key;
        this.value = value;
    };

}
