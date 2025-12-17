package com.jonuzovic.ragesetz.infrastructure.ai.service;

import com.jonuzovic.ragesetz.core.exception.LlmResponseParsingException;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.response.AdviceResponse;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;
import com.jonuzovic.ragesetz.core.response.RelevanceResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
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
        try {
            StringBuilder relevantLawsString = new StringBuilder();
            for (Law law : relevantLaws) {
                relevantLawsString.append(law.toString());
            }
            String prompt = """
                    You are a law expert for german laws.
                    This is a RAG Pipeline application. You will also get relevant context from database about users question.
                    Now you need to give advice to user about certain legal situation.
                    The user formed the situation in form of a question to you.
                    Here follow the users question about the law, and the relevant context from vector database.
                    
                    User question:
                    
                    %s
                    
                    Law:
                    
                    %s
                    
                    Anwser in german language.
                    Anwser the question to the user.
                    """.formatted(userQuestion, relevantLawsString.toString());

            String llmAdvice = lawExpertModelClient.prompt().user(prompt).call().chatResponse()
                    .getResult().getOutput().getText();
            return AdviceResponse.builder()
                    .generatedAdvice(llmAdvice)
                    .relevantLawsForQuestion(relevantLaws)
                    .build();
        } catch (Exception e) {
            throw new LlmResponseParsingException(e, userQuestion);
        }
    }

    @Override
    public LawExplanationResponse explainLaw(String userQuestion, String lawAsString) {
        try {
            String prompt = """
                    You are a law expert for german laws.
                    Now you need to explain a certain law.
                    Here follow the users question about the law, and the actual law content.
                    
                    User question:
                    
                    %s
                    
                    Law:
                    
                    %s
                    
                    Anwser in german language.
                    Answer in JSON format exactly like this.
                    {
                     "lawCode": "string",
                     "lawSectionNumber": "string",
                     "lawTitle": "string",
                     "lawContent": "string",
                     "generatedExplanation": "string"
                    }
                    """.formatted(userQuestion, lawAsString);
            return lawExpertModelClient.prompt()
                    .user(prompt)
                    .call()
                    .entity(LawExplanationResponse.class);
        } catch (Exception e) {
            throw new LlmResponseParsingException(e, userQuestion);
        }
    }

    @Override
    public RelevanceResponse checkQuestionRelevance(String userQuestion) {
        try {
            String prompt = """
                    You are a law expert for german laws.
                    Check if the following question is relevant for german laws context.
                    
                    User question:
                    
                    %s
                    
                    Anwser in german language.
                    Answer in JSON format exactly like this. Set the isRelevant variable to either true of false and write the user a message.
                    If the users question is irrelevant to the GlobalX database contents explain to the user in the message that you are only able to answer questions about GlobalX database and nothing else.
                    {
                     "isRelevant": boolean,
                     "message": "string",
                    }
                    """.formatted(userQuestion);

            return lightModelClient.prompt()
                    .user(prompt)
                    .call()
                    .entity(RelevanceResponse.class);
        } catch (Exception e) {
            throw new LlmResponseParsingException(e, userQuestion);
        }
    }
}
