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
	public String askAbout(String userQuestion, String relevantContext) {
		String prompt = """
	            Du bist ein assistent fuer Fragen zur Deutschen Gesetzen.
	            Basieren auf den gefundenen relevanten Daten aus der Gesetzesdatenbank,benatworte die Frage des Nutzers.
	            
	            Relevante Gesetzesdaten:
	            %s
	            
	            Frage des Nutzers:
	            %s
	            """.formatted(relevantContext, userQuestion);

        ChatResponse response = chatClient.prompt()
            .user(prompt)
            .call()
            .chatResponse();

        return response == null ? askAbout(userQuestion, relevantContext) : response.getResult().getOutput().getText();
	}

	@Override
	public String explainLaw(LawDto law) {
		return null;
	}

}
