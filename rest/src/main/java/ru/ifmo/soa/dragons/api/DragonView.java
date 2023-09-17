package ru.ifmo.soa.dragons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.app.sql.order.Order;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.dragons.api.schema.CreateDragonRequest;
import ru.ifmo.soa.dragons.api.schema.ListOfDragonsResponse;
import ru.ifmo.soa.dragons.api.schema.SumResponse;
import ru.ifmo.soa.dragons.api.schema.UpdateDragonRequest;
import ru.ifmo.soa.dragons.api.validation.CreateDragonRequestValidator;
import ru.ifmo.soa.dragons.api.validation.UpdateDragonRequestValidator;
import ru.ifmo.soa.dragons.model.Color;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.model.DragonType;
import ru.ifmo.soa.dragons.service.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/dragons", produces = MediaType.APPLICATION_XML_VALUE )
public class DragonView {

    @Autowired
    CreateDragonRequestValidator createDragonRequestValidator;

    @Autowired
    UpdateDragonRequestValidator updateDragonRequestValidator;

    @Autowired
    DragonCreator dragonCreator;

    @Autowired
    DragonGetter dragonGetter;

    @Autowired
    DragonUpdater dragonUpdater;

    @Autowired
    DragonDeleter dragonDeleter;

    @Autowired
    DragonUtils dragonUtils;

    @PostMapping(value = "/")
    public ResponseEntity<?> create(@RequestParam("dragon") final String createDragonRequestString) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();
        try {
            CreateDragonRequest request = mapper.readValue(createDragonRequestString, CreateDragonRequest.class);
            ValidatedData<CreateDragonRequest, CreateDragonRequestValidator> validatedData = new ValidatedData<>(request, createDragonRequestValidator);
            Dragon dragon = dragonCreator.create(validatedData);
            return ResponseEntity.status(200).body(dragon);

        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(400).build();
        } catch (ValidationError error) {
            return ResponseEntity.status(400).body(error.getErrors());
        }


    }

    @GetMapping(value = "/")
    public ResponseEntity<?> list(
            @RequestParam(value = "filter", required = false) String filterString,
            @RequestParam(value = "order", required = false) String orderString,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    ) throws SQLException, InvalidFilter, ServiceError {

        try {

            List<Filter<String>> filters = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();

            if (filterString != null)
                filters = mapper.readValue(filterString, new TypeReference<>() {
                });

            if (orderString != null)
                orders = mapper.readValue(orderString, new TypeReference<List<Order>>() {
                });


            List<Dragon> dragons = dragonGetter.getDragons(filters, orders, limit, offset);

            return ResponseEntity.ok().body(new ListOfDragonsResponse(dragons));


        } catch (JsonProcessingException ex) {
            return ResponseEntity.badRequest().build();
        } catch (ValidationError error) {
            return ResponseEntity.badRequest().body(error.getErrors());
        }
    }


    @GetMapping(value = "/count-by-type")
    public ResponseEntity<?> countByType(@RequestParam("type") DragonType type) throws ServiceError{

        return ResponseEntity.ok().body(new SumResponse(dragonUtils.countDragonsByType(type)));

    }

    @GetMapping(value = "/filter-by-color")
    public ResponseEntity<?> countByType(
            @RequestParam("color") Color color,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    ) throws ServiceError {

        return ResponseEntity.ok().body(new ListOfDragonsResponse(dragonUtils.filterByColor(color, limit, offset)));

    }

    @GetMapping(value = "/sum-age")
    public ResponseEntity<?> sumAge() throws ServiceError {

        return ResponseEntity.ok().body(new SumResponse(dragonUtils.sumAge()));

    }


    @GetMapping(value = "/{dragonId}")
    public ResponseEntity<?> getById(@PathVariable("dragonId") Long dragonId) throws ServiceError {

        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
        if (mbDragon.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(mbDragon.get());

    }

    @PutMapping(value = "/{dragonId}")
    public ResponseEntity<?> update(
            @RequestParam("dragon")  final String updateDragonRequestString,
            @PathVariable("dragonId") Long dragonId
    ) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
            if (mbDragon.isEmpty())
                return ResponseEntity.notFound().build();

            UpdateDragonRequest request = mapper.readValue(updateDragonRequestString, UpdateDragonRequest.class);
            ValidatedData<UpdateDragonRequest, UpdateDragonRequestValidator> validatedData = new ValidatedData<>(request, updateDragonRequestValidator);

            Dragon updated = dragonUpdater.update(validatedData, mbDragon.get());

            return ResponseEntity.ok().body(updated);
        } catch (JsonProcessingException ex) {
            return ResponseEntity.badRequest().build();
        } catch (ValidationError error) {
            return ResponseEntity.badRequest().body(error.getErrors());
        }

    }

    @DeleteMapping("/{dragonId}")
    public ResponseEntity<?> delete(
            @RequestParam("dragonId") Long dragonId
    ) throws ServiceError {


        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
        if (mbDragon.isEmpty())
            return ResponseEntity.notFound().build();


        dragonDeleter.delete(mbDragon.get());

        return ResponseEntity.noContent().build();


    }
}
