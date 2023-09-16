package ru.ifmo.soa.service1.dragons.repository;

import ru.ifmo.soa.service1.app.sql.filter.FilterSet;

import java.util.Set;

public class DragonFilterSet extends FilterSet {
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
