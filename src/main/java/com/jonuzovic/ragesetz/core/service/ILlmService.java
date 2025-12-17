package com.jonuzovic.ragesetz.core.service;

import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.response.AdviceResponse;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;
import com.jonuzovic.ragesetz.core.response.RelevanceResponse;

import java.util.List;

public interface ILlmService {
    AdviceResponse giveAdvice(String userQuestion, List<Law> relevantLaws);
    LawExplanationResponse explainLaw(String userQuestion, Law law);
    RelevanceResponse checkQuestionRelevance(String userQuestion);
}
