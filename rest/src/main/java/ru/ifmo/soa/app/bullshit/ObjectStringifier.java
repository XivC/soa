package ru.ifmo.soa.app.bullshit;

public class ObjectStringifier {

    public static String perform(Object object){
        if (object == null) return null;
        return object.toString();
    }

}
