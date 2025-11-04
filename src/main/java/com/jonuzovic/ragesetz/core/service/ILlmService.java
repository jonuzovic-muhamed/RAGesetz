package com.jonuzovic.ragesetz.core.service;

import com.jonuzovic.ragesetz.api.dto.LawDto;

public interface ILlmService {
	
	public String chat(String message);

	public String askAboutLaw(String userQuestion, String relevantContext);
	
	public String explainLaw(LawDto law);
	
}
