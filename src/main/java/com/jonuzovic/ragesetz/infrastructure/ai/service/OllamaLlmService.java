package com.jonuzovic.ragesetz.infrastructure.ai.service;

import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.response.AdviceResponse;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;
import com.jonuzovic.ragesetz.core.response.RelevanceResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jonuzovic.ragesetz.core.service.ILlmService;

import java.util.List;

@Service("ollamaLlmService")
public class OllamaLlmService implements ILlmService {

    private final ChatClient lawExpertModelClient;
    private final ChatClient lightModelClient;

    public OllamaLlmService(
            @Qualifier("lawExpertClient") ChatClient lawExpertModelClient,
            @Qualifier("lightClient") ChatClient lightModelClient) {
        this.lawExpertModelClient = lawExpertModelClient;
        this.lightModelClient = lightModelClient;
    }

    @Override
    public AdviceResponse giveAdvice(String userQuestion, List<Law> relevantLaws) {
        return null;
    }

    @Override
    public LawExplanationResponse explainLaw(String userQuestion, Law law) {
        return null;
    }

    @Override
    public RelevanceResponse checkQuestionRelevance(String userQuestion) {
        return null;
    }
}
