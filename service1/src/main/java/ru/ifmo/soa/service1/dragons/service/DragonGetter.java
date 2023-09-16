package ru.ifmo.soa.service1.dragons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.sql.filter.Filter;
import ru.ifmo.soa.service1.app.sql.filter.FilterSet;
import ru.ifmo.soa.service1.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.service1.app.sql.order.InvalidOrder;
import ru.ifmo.soa.service1.app.sql.order.Order;
import ru.ifmo.soa.service1.app.validation.ValidationError;
import ru.ifmo.soa.service1.dragons.model.Dragon;
import ru.ifmo.soa.service1.dragons.repository.DragonFilterSet;
import ru.ifmo.soa.service1.dragons.repository.DragonOrderSet;
import ru.ifmo.soa.service1.dragons.repository.DragonRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class DragonGetter {

    @Inject
    DragonRepository dragonRepository;

    public List<Dragon> getDragons(List<Filter<String>> filters, List<Order> orders, Integer limit, Integer offset) throws ServiceError, ValidationError {

        DragonFilterSet filterSet = getFilterSet(filters);
        DragonOrderSet orderSet = getOrderSet(orders);

        try {
            return dragonRepository.getList(filterSet, orderSet, limit, offset);
        }
        catch (SQLException ex) {
            throw new ServiceError();
        }

    }

    public Optional<Dragon> getById(Long id) throws ServiceError{
        try {
            return dragonRepository.getById(id);
        }
        catch (SQLException ex) {
            throw new ServiceError();
        }
    }


    private DragonFilterSet getFilterSet(List<Filter<String>> filters) throws ValidationError{
        DragonFilterSet filterSet = new DragonFilterSet();
        List<String> errors = new ArrayList<>();
        for (Filter<String> f: filters){
            try {
                filterSet.add(f);
            }
            catch (InvalidFilter ex){
                errors.add(String.format("Filter %s is not allowed", f.getKey()));
            }
        }

        if (!errors.isEmpty()) throw new ValidationError(errors);
        return filterSet;

    }

    private DragonOrderSet getOrderSet(List<Order> orders) throws ValidationError{
        DragonOrderSet orderSet = new DragonOrderSet();
        List<String> errors = new ArrayList<>();
        for (Order o: orders){
            try {
                orderSet.add(o);
            }
            catch (InvalidOrder ex){
                errors.add(String.format("Order %s is not allowed", o.getKey()));
            }
        }

        if (!errors.isEmpty()) throw new ValidationError(errors);
        return orderSet;

    }

}
