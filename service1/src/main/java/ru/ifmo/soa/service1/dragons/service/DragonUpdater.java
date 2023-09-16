package ru.ifmo.soa.service1.dragons.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.soa.service1.app.service.ServiceError;
import ru.ifmo.soa.service1.app.sql.filter.Filter;
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
public class DragonUpdater {

    @Inject
    DragonRepository dragonRepository;


}
