package com.jonuzovic.ragesetz.infrastructure.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import com.jonuzovic.ragesetz.api.dto.LawDto;
import com.jonuzovic.ragesetz.core.service.ILlmService;

@Service("ollamaLlmService")
public class OllamaLlmService implements ILlmService {
	

    private final ChatClient chatClient;

    public OllamaLlmService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

	@Override
	public String chat(String message) {
		 ChatResponse response = chatClient.prompt()
		            .user(message)
		            .call()
		            .chatResponse();
        return response == null ? "Entschuldigung, die Frage könnte nicht beantworter werden. Bitte Frage nochmal!" : response.getResult().getOutput().getText();
	}

	@Override
	public String askAboutLaw(String userQuestion, String relevantContext) {
		String prompt = """
	            Relevante Gesetzesdaten:
	            %s
	            
	            Frage des Nutzers:
	            %s
	            """.formatted(relevantContext, userQuestion);

        ChatResponse response = chatClient.prompt()
            .user(prompt)
            .call()
            .chatResponse();

        return response == null ? askAboutLaw(userQuestion, relevantContext) : response.getResult().getOutput().getText();
	}

	@Override
	public String explainLaw(LawDto law) {
		return null;
	}

}
