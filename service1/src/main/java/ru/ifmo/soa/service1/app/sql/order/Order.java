package ru.ifmo.soa.service1.app.sql.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

    String key;
    OrderType orderType;

    public Order(String key, OrderType orderType){
        this.key = key;
        this.orderType = orderType;
    };

}
