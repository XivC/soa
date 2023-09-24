package ru.ifmo.soa.killer;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.ifmo.soa.killer.auth.DummyAuthorization;

import java.util.Set;

@ApplicationPath("/killer")
public class KillerApplication extends Application {



}