package com.jonuzovic.ragesetz.core.service;

import com.jonuzovic.ragesetz.core.response.AdviceResponse;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;

public interface IChatService {
    AdviceResponse askForAdvice(String userQuestion);
    LawExplanationResponse askForLawExplanation(String userQuestion, String lawCode, String lawSectionNumber);
}