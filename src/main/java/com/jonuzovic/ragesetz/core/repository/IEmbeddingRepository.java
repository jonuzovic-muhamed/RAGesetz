package com.jonuzovic.ragesetz.core.repository;

import java.sql.Date;
import java.util.List;

import com.jonuzovic.ragesetz.core.model.Law;

public interface IEmbeddingRepository {

    void deleteOlderThan(Date date);

    boolean isTableEmpty();

    List<Law> getAllEmbeddings();

    List<Law> findRelevantEmbeddings(List<Float> embedding, Integer limit);
    
}
