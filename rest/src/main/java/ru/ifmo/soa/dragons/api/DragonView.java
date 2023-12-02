package ru.ifmo.soa.dragons.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.PayloadEndpoint;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.ifmo.soa.app.schema.ErrorResponse;
import ru.ifmo.soa.app.service.ServiceError;
import ru.ifmo.soa.app.sql.filter.Filter;
import ru.ifmo.soa.app.sql.filter.InvalidFilter;
import ru.ifmo.soa.app.sql.order.Order;
import ru.ifmo.soa.app.validation.ValidatedData;
import ru.ifmo.soa.app.validation.ValidationError;
import ru.ifmo.soa.dragons.api.converter.DragonConverter;
import ru.ifmo.soa.dragons.api.schema.*;
;
import ru.ifmo.soa.dragons.api.validation.CreateDragonRequestValidator;
import ru.ifmo.soa.dragons.api.validation.UpdateDragonRequestValidator;
import ru.ifmo.soa.dragons.model.Color;
import ru.ifmo.soa.dragons.model.Dragon;
import ru.ifmo.soa.dragons.model.DragonType;
import ru.ifmo.soa.dragons.service.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Endpoint
public class DragonView {

    private static final String NAMESPACE_URI = "http://ru/ifmo/soa/";

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

    @Autowired
    DragonConverter dragonConverter;

//    @PostMapping(value = "/")
//    public ResponseEntity<?> create(@RequestParam("dragon") String createDragonRequestString) throws ServiceError {
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            CreateDragonRequest request = mapper.readValue(createDragonRequestString, CreateDragonRequest.class);
//            ValidatedData<CreateDragonRequest, CreateDragonRequestValidator> validatedData = new ValidatedData<>(request, createDragonRequestValidator);
//            Dragon dragon = dragonCreator.create(validatedData);
//            return ResponseEntity.status(201).body(dragon);
//
//        } catch (JsonProcessingException ex) {
//            return ResponseEntity.status(400).build();
//        } catch (ValidationError error) {
//            return ResponseEntity.status(400).body(new ErrorResponse(error.getErrors()));
//        }
//
//
//    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonsRequest")
    @ResponsePayload
    public JAXBElement<?> list(
            @RequestPayload GetDragonsRequest request
            ) throws SQLException, InvalidFilter, ServiceError {
        try {

            List<Filter<String>> filters = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();

            if (request.getFilter() != null)
                filters = mapper.readValue(request.getFilter(), new TypeReference<List<Filter<String>>>() {
                });

            if (request.getOrder() != null)
                orders = mapper.readValue(request.getOrder(), new TypeReference<List<Order>>() {
                });


            List<Dragon> dragons = dragonGetter.getDragons(filters, orders, request.getLimit(), request.getOffset());

            return new JAXBElement<>(
                    QName.valueOf("ListOfDragonsResponse"),
                    ListOfDragonsResponse.class,
                    dragonConverter.toListResponse(dragons)
            );


        } catch (JsonProcessingException ex) {
            return new ErrorResponse(List.of("Invalid Request")).asResponse();
        } catch (ValidationError error) {
            return new ErrorResponse(error.getErrors()).asResponse();
        }
    }



//    @GetMapping(value = "/count-by-type/")
//    public ResponseEntity<?> countByType(@RequestParam("type") DragonType type) throws ServiceError{
//
//        return ResponseEntity.ok().body(new SumResponse(dragonUtils.countDragonsByType(type)));
//
//    }
//
////    @GetMapping(value = "/filter-by-color/")
////    public ResponseEntity<?> countByType(
////            @RequestParam("color") Color color,
////            @RequestParam(value = "limit", required = false) Integer limit,
////            @RequestParam(value = "offset", required = false) Integer offset
////    ) throws ServiceError {
////
////        return ResponseEntity.ok().body(new ListOfDragonsResponse(dragonUtils.filterByColor(color, limit, offset)));
////
////    }
//
//    @GetMapping(value = "/sum-age/")
//    public ResponseEntity<?> sumAge() throws ServiceError {
//
//        return ResponseEntity.ok().body(new SumResponse(dragonUtils.sumAge()));
//
//    }
//
//
//    @GetMapping(value = "/{dragonId}/")
//    public ResponseEntity<?> getById(@PathVariable("dragonId") Long dragonId) throws ServiceError {
//
//        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
//        if (!mbDragon.isPresent()) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok().body(mbDragon.get());
//
//    }
//
//    @PutMapping(value = "/{dragonId}/")
//    public ResponseEntity<?> update(
//            @RequestParam("dragon")  final String updateDragonRequestString,
//            @PathVariable("dragonId") Long dragonId
//    ) throws ServiceError {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
//            if (!mbDragon.isPresent())
//                return ResponseEntity.notFound().build();
//
//            UpdateDragonRequest request = mapper.readValue(updateDragonRequestString, UpdateDragonRequest.class);
//            ValidatedData<UpdateDragonRequest, UpdateDragonRequestValidator> validatedData = new ValidatedData<>(request, updateDragonRequestValidator);
//
//            Dragon updated = dragonUpdater.update(validatedData, mbDragon.get());
//
//            return ResponseEntity.ok().body(updated);
//        } catch (JsonProcessingException ex) {
//            return ResponseEntity.badRequest().build();
//        } catch (ValidationError error) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(error.getErrors()));
//        }
//
//    }
//
//    @DeleteMapping("/{dragonId}/")
//    public ResponseEntity<?> delete(
//            @PathVariable("dragonId") Long dragonId
//    ) throws ServiceError {
//
//
//        Optional<Dragon> mbDragon = dragonGetter.getById(dragonId);
//        if (!mbDragon.isPresent())
//            return ResponseEntity.notFound().build();
//
//
//        dragonDeleter.delete(mbDragon.get());
//
//        return ResponseEntity.noContent().build();
//
//
//    }
}
