package ru.ifmo.soa.dragons.repository;

import ru.ifmo.soa.app.sql.order.OrderSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DragonOrderSet extends OrderSet {
    @Override
    protected Set<String> getPossibleFields() {
        return new HashSet<>(
                Arrays.asList(
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
                )
        );
    }
}
