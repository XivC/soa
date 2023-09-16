package ru.ifmo.soa.service1.dragons.repository;

import ru.ifmo.soa.service1.app.sql.order.OrderSet;

import java.util.Set;

public class DragonOrderSet extends OrderSet {
    @Override
    protected Set<String> getPossibleFields() {
        return Set.of(
                "id",
                "name",
                "coordinate_x",
                "coordinate_y",
                "creation_date",
                "age",
                "color",
                "type",
                "character",
                "killer_id"
        );
    }
}
