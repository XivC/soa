package ru.ifmo.soa.killer.service;

import ru.ifmo.soa.killer.result.CaveCreateResult;

import javax.ejb.Remote;

@Remote
public interface CaveCreator {

    CaveCreateResult create(Long id);
}
