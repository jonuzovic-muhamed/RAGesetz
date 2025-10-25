package com.jonuzovic.ragesetz.api.dao;

import java.util.Optional;

import com.jonuzovic.ragesetz.api.model.Law;

public interface IDataAccessObject {

    Long create(Law law);

    Optional<Law> findById(Long id);

    void update(Law law);

    Long deleteById(Long id);

    void deleteAll();
    
}
