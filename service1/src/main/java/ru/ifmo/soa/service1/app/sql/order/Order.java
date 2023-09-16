package ru.ifmo.soa.service1.app.sql.order;

import lombok.Getter;

@Getter
public class Order {

    String key;
    OrderType orderType;

    public Order(String key, OrderType orderType){
        this.key = key;
        this.orderType = orderType;
    };

}
