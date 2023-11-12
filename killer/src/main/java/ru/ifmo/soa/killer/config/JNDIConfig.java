package ru.ifmo.soa.killer.config;

import ru.ifmo.soa.killer.service.CaveCreator;
import ru.ifmo.soa.killer.service.DragonKiller;
import ru.ifmo.soa.killer.service.TeamCreator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JNDIConfig {


    public static DragonKiller dragonKiller() {
        return lookup("DragonKiller");
    }

    public static TeamCreator teamCreator() {
        return lookup("TeamCreator");
    }

    public static CaveCreator caveCreator() {
        return lookup("CaveCreator");
    }

    public static <T> T lookup(String name) {
        try {
            Properties environment = new Properties();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, "fish.payara.ejb.http.client.RemoteEJBContextFactory");
            environment.put(Context.PROVIDER_URL, "http://localhost:8081/ejb-invoker");
            InitialContext ejbRemoteContext = new InitialContext(environment);
            T t = (T) ejbRemoteContext.lookup("java:global/killer-logic-1/" + name + "Impl");
            System.out.println("Пполучилось :)");
            return t;
        } catch (NamingException e) {
            System.out.println("Не получилось :(");
            e.printStackTrace();
            return null;
        }
    }
}