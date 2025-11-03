package com.jonuzovic.ragesetz.infrastructure.embedder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jonuzovic.ragesetz.core.embedder.ILawEmbedder;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.repository.ICrudRepository;
import com.jonuzovic.ragesetz.core.repository.IEmbeddingRepository;
import com.jonuzovic.ragesetz.core.service.IEmbeddingService;

@Service("lawEmbdedderService")
public class LawEmbedderService implements ILawEmbedder {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final IEmbeddingService embeddingService;
	private final ICrudRepository crudRepository;
	private final IEmbeddingRepository embeddingRepository;
	
	public LawEmbedderService(IEmbeddingService embeddingService, ICrudRepository crudRepository, IEmbeddingRepository embeddingRepository) {
		this.embeddingService = embeddingService;
		this.crudRepository = crudRepository;
		this.embeddingRepository = embeddingRepository;
	}

	@Override
	public void embedLaws(List<Law> laws) {
		if (!embeddingRepository.isTableEmpty()) {
			log.info("Skip embedding process, there are existing embeddings in database!");
			return;
		}
		
		for (Law law : laws) {
			List<Float> embedding = embeddingService.embedText(law.getLawCode() + law.getLawSectionNumber() + law.getLawTitle() + law.getLawContent());
			law.setLawEmbedding(embedding);
			crudRepository.create(law);
		}
	}

}
