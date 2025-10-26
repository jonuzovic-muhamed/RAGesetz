package com.jonuzovic.ragesetz.core.repository;

import java.util.Optional;

import com.jonuzovic.ragesetz.core.model.Law;

public interface ICrudRepository {

    Long create(Law law);

    Optional<Law> findById(Long id);

    void update(Law law);

    Long deleteById(Long id);

    void deleteAll();
    
}
