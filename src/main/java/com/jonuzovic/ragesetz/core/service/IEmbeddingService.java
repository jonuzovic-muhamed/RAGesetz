package com.jonuzovic.ragesetz.core.service;

import java.util.List;

public interface IEmbeddingService {

	List<Float> embedText(String text);
}
