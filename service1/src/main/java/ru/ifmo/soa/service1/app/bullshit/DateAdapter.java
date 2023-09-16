package ru.ifmo.soa.service1.app.bullshit;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";

    @Override
    public String marshal(LocalDate v) {
        return v.format(DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING));
    }

    @Override
    public LocalDate unmarshal(String v) throws ParseException {
        return LocalDate.parse(v, DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING));
    }

}