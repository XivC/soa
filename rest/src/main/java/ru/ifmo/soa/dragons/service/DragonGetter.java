package ru.ifmo.soa.dragons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.app.sql.order.InvalidOrder;
import ru.ifmo.soa.app.sql.order.Order;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.repository.DragonFilterSet;
import ru.ifmo.soa.dragons.repository.DragonFilterSetValidator;
import ru.ifmo.soa.dragons.repository.DragonOrderSet;
import ru.ifmo.soa.dragons.repository.DragonRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DragonGetter {

    @Autowired
    DragonRepository dragonRepository;

    @Autowired
    DragonFilterSetValidator dragonFilterSetValidator;

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

        errors.addAll(dragonFilterSetValidator.validate(filterSet));


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
