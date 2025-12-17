package com.jonuzovic.ragesetz.infrastructure.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import com.jonuzovic.ragesetz.core.service.IEmbeddingService;


@Service("ollamaEmbeddingService")
public class OllamaEmbeddingService implements IEmbeddingService {
	
	private final EmbeddingModel embeddingModel;

    public OllamaEmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public List<Float> embedText(String text) {
        float[] result = embeddingModel.embed(text);
        List<Float> embedding = new ArrayList<>();
        for (float vector : result) {
            embedding.add(vector);
        }
        return embedding;
    }

}
