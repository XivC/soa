package ru.ifmo.soa.app.sql.order;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public abstract class OrderSet {

    List<Order> orders;

    public OrderSet(){
        this.orders = new LinkedList<>();
    }

    public OrderSet add(Order order) throws InvalidOrder {
        if (!this.getPossibleFields().contains(order.getKey())) throw new InvalidOrder();
        this.orders.add(order);
        return this;
    }

    protected abstract Set<String> getPossibleFields();

}
