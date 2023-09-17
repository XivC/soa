package ru.ifmo.soa.app.sql.filter;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public abstract class FilterSet {

    List<Filter<?>> filters;

    public FilterSet(){
        this.filters = new LinkedList<>();
    }

    public FilterSet add(Filter<?> filter) throws InvalidFilter {
        if (!this.getPossibleFields().contains(filter.getKey())) throw new InvalidFilter();
        this.filters.add(filter);
        return this;
    }

    protected abstract Set<String> getPossibleFields();

}
