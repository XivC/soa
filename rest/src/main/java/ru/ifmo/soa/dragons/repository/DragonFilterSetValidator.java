package ru.ifmo.soa.dragons.repository;

import org.springframework.stereotype.Service;
import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DragonFilterSetValidator implements Validator<DragonFilterSet> {
    @Override
    public List<String> validate(DragonFilterSet obj) {
        List<String> errors = new ArrayList<>();
        for (Filter<?> filter : obj.getFilters()){

            if (Objects.equals(filter.getKey(), "creation_date")){
                try {
                    LocalDate.parse(filter.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                catch (DateTimeParseException ex){
                    errors.add("creation_date has invalid value");
                }
            }

        }
        return errors;
    }
}
