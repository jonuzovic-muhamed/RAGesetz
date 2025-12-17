package com.jonuzovic.ragesetz.infrastructure.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

    private final static String LAW_MODEL = System.getenv("OLLAMA_LAW_MODEL");
    private final static String LIGHT_MODEL = System.getenv("OLLAMA_LIGHT_MODEL");

    @Bean("lightClient")
    public ChatClient lightweightModel(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel)
                .defaultOptions(ChatOptions.builder()
                        .model(LIGHT_MODEL)
                        .build())
                .build();
    }

    @Bean("lawExpertClient")
    public ChatClient lawExpert(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel)
                .defaultOptions(ChatOptions.builder()
                        .model(LAW_MODEL)
                        .temperature(0.0)       // No creativity — pure logic
                        .topK(20)               // Reduces the probability of generating nonsense. Default is values is 40. A higher value (e.g., 100) will give more diverse answers, while a lower value (e.g., 10) will be more conservative.
                        .topP(0.5)              // Works together with top-k. Default value is 0.9. A higher value (e.g., 0.95) will lead to more diverse text, while a lower value (e.g., 0.5) will generate more focused and conservative text.
                        .maxTokens(512)         // Ensure large enough for complex queries
                        .presencePenalty(0.0)   // No penalty for repetition
                        .frequencyPenalty(0.0)  // No penalty for repetition
                        .build())
                .build();
    }
}
