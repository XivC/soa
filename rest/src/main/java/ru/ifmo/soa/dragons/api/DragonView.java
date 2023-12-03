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
import ru.ifmo.soa.app.schema.SuccessResponse;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createDragonRequest")
    @ResponsePayload
    public JAXBElement<?> create(@RequestPayload CreateDragonRequest request) throws ServiceError {

        try {
            ValidatedData<CreateDragonRequest, CreateDragonRequestValidator> validatedData = new ValidatedData<>(request, createDragonRequestValidator);
            Dragon dragon = dragonCreator.create(validatedData);
            return new JAXBElement<DragonResponse>(
                    QName.valueOf("dragonResponse"),
                    DragonResponse.class,
                    dragonConverter.toResponse(dragon)
            );

        }  catch (ValidationError error) {
            return new ErrorResponse(error.getErrors()).asResponse();
        }


    }

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



    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "countByTypeRequest")
    @ResponsePayload
    public SumResponse countByType(@RequestPayload CountByTypeRequest request) throws ServiceError{

        return new SumResponse(dragonUtils.countDragonsByType(request.getType()));

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "filterByColorRequest")
    @ResponsePayload
    public JAXBElement<?> filterByColor(
            @RequestPayload FilterByColorRequest request
    ) throws ServiceError {

        List<Dragon> dragons = dragonUtils.filterByColor(request.getColor(), request.getLimit(), request.getOffset());
        return new JAXBElement<>(
                QName.valueOf("ListOfDragonsResponse"),
                ListOfDragonsResponse.class,
                dragonConverter.toListResponse(dragons)
        );

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sumAgeRequest")
    @ResponsePayload
    public SumResponse sumAge() throws ServiceError {

        return new SumResponse(dragonUtils.sumAge());

    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonRequest")
    @ResponsePayload
    public JAXBElement<?> getById(@RequestPayload GetDragonRequest request) throws ServiceError {

        Optional<Dragon> mbDragon = dragonGetter.getById(request.getId());
        if (!mbDragon.isPresent()) return new ErrorResponse(List.of("Not found")).asResponse();
        return new JAXBElement<DragonResponse>(
                QName.valueOf("dragonResponse"),
                DragonResponse.class,
                dragonConverter.toResponse(mbDragon.get())
        );

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateDragonRequest")
    @ResponsePayload
    public JAXBElement<?> update(
            @RequestPayload UpdateDragonRequest request
    ) throws ServiceError {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Optional<Dragon> mbDragon = dragonGetter.getById(request.getId());
            if (!mbDragon.isPresent())
                return new ErrorResponse(List.of("Not found")).asResponse();

            ValidatedData<UpdateDragonRequest, UpdateDragonRequestValidator> validatedData = new ValidatedData<>(request, updateDragonRequestValidator);

            Dragon updated = dragonUpdater.update(validatedData, mbDragon.get());

            return new JAXBElement<DragonResponse>(
                    QName.valueOf("dragonResponse"),
                    DragonResponse.class,
                    dragonConverter.toResponse(updated)
            );
        }  catch (ValidationError error) {
            return new ErrorResponse(error.getErrors()).asResponse();
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDragonRequest")
    @ResponsePayload
    public JAXBElement<?> delete(
            @RequestPayload DeleteDragonRequest request
    ) throws ServiceError {


        Optional<Dragon> mbDragon = dragonGetter.getById(request.getId());
        if (!mbDragon.isPresent())
            return new ErrorResponse(List.of("Not found")).asResponse();


        dragonDeleter.delete(mbDragon.get());

        return new SuccessResponse(List.of("Deleted")).asResponse();


    }
}
