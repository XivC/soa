package ru.ifmo.soa.app.sql;

import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.sql.filter.FilterSet;
import ru.ifmo.soa.app.sql.order.Order;
import ru.ifmo.soa.app.sql.order.OrderSet;

import java.util.ArrayList;

public class SQLBuilder {
    String baseSql;
    FilterSet filterSet;
    OrderSet orderSet;

    Integer limit;

    Integer offset;

    public SQLBuilder base(String sql){
        this.baseSql = sql;
        return this;
    }
    public SQLBuilder filter(FilterSet filterSet){
        this.filterSet = filterSet;
        return this;

    }

    public SQLBuilder order(OrderSet orderSet){
        this.orderSet = orderSet;
        return this;
    }

    private String getFilters(){
        if (this.filterSet == null  || this.filterSet.getFilters().isEmpty())
            return "";

        StringBuilder sql = new StringBuilder("WHERE ");
        ArrayList<String> filtersStrings = new ArrayList<>();
        for (Filter<?> filter: filterSet.getFilters()){
            filtersStrings.add(
                    String.format("\"%s\"='%s'", filter.getKey(), filter.getValue().toString())
            );
        }
        sql.append(String.join(",", filtersStrings));
        return sql.toString();
    }

    public SQLBuilder limit (Integer limit) {
        this.limit = limit;
        return this;
    }

    public SQLBuilder offset (Integer offset){
        this.offset = offset;
        return this;
    }

    private String getOrders(){
        if (this.orderSet == null  || this.orderSet.getOrders().isEmpty())
            return "";

        StringBuilder sql = new StringBuilder("ORDER BY ");
        ArrayList<String> orderStrings = new ArrayList<>();
        for (Order order: orderSet.getOrders()){
            orderStrings.add(
                    String.format("\"%s\" %s", order.getKey(), order.getOrderType().toString())
            );
        }
        sql.append(String.join(",", orderStrings));
        sql.append(" ");
        return sql.toString();
    }

    private String getPagination(){
        StringBuilder sql = new StringBuilder();
        if (this.limit != null) sql.append(
                String.format("LIMIT %s ", this.limit)
        );
        if (this.offset != null) sql.append(
                String.format("OFFSET %s ", this.offset)
        );
        return sql.toString();
    }

    public String build(){
        return this.baseSql + " " + this.getFilters() + " " + this.getOrders() + this.getPagination() +  ";";
    }
}
