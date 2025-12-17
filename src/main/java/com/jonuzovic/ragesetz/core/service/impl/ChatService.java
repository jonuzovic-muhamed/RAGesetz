package com.jonuzovic.ragesetz.core.service.impl;

import com.jonuzovic.ragesetz.core.exception.IrrelevantQuestionException;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.repository.ICrudRepository;
import com.jonuzovic.ragesetz.core.repository.IEmbeddingRepository;
import com.jonuzovic.ragesetz.core.response.AdviceResponse;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;
import com.jonuzovic.ragesetz.core.response.RelevanceResponse;
import com.jonuzovic.ragesetz.core.service.IChatService;
import com.jonuzovic.ragesetz.core.service.IEmbeddingService;
import com.jonuzovic.ragesetz.core.service.ILlmService;

import java.util.List;
import java.util.Optional;

public class ChatService implements IChatService {

    private static final String LIMIT = System.getenv("LLM_CONTEXT_LIMIT");

    private final IEmbeddingService embeddingService;
    private final IEmbeddingRepository embeddingRepository;
    private final ICrudRepository crudRepository;
    private final ILlmService llmService;

    public ChatService(IEmbeddingService embeddingService, IEmbeddingRepository embeddingRepository, ICrudRepository crudRepository, ILlmService illmService, ILlmService llmService) {
        this.embeddingService = embeddingService;
        this.embeddingRepository = embeddingRepository;
        this.crudRepository = crudRepository;
        this.llmService = llmService;
    }

    @Override
    public AdviceResponse askForAdvice(String userQuestion) {
        List<Law> relevantLaws = embeddingRepository.findRelevantEmbeddings(
                embeddingService.embedText(userQuestion),
                Integer.valueOf(LIMIT)
        );
        RelevanceResponse relevanceResponse = llmService.checkQuestionRelevance(userQuestion);
        if (!relevanceResponse.getIsRelevant()) {
            throw new IrrelevantQuestionException(userQuestion, "Question is not relevant for german law!");
        }
        return llmService.giveAdvice(userQuestion, relevantLaws);
    }

    @Override
    public LawExplanationResponse askForLawExplanation(String userQuestion, String lawCode, String lawSectionNumber) {
        Optional<Law> law = crudRepository.findByLawCodeAndSectionNumber(lawCode,lawSectionNumber);
        RelevanceResponse relevanceResponse = llmService.checkQuestionRelevance(userQuestion);
        String lawString = law.isPresent() ? law.get().toString() : "No law was found in database that corresponds to the users input.";
        LawExplanationResponse explanationResponse;
        if (relevanceResponse.getIsRelevant()) {
            explanationResponse = llmService.explainLaw(userQuestion,lawString);
        } else {
            throw new IrrelevantQuestionException(userQuestion, "Irrelevant question for german law!");
        }
        return explanationResponse;
    }
}
