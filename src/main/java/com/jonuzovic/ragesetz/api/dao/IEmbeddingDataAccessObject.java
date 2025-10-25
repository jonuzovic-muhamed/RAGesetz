package com.jonuzovic.ragesetz.api.dao;

import java.sql.Date;
import java.util.List;

import com.jonuzovic.ragesetz.api.model.Law;

public interface IEmbeddingDataAccessObject {

    void deleteOlderThan(Date date);

    boolean isTableEmpty();

    List<Law> getAllEmbeddings();

    List<Law> findRelevantEmbeddings(List<Float> embedding, Integer limit);
    
}
