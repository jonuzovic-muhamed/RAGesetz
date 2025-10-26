package com.jonuzovic.ragesetz.core.service;

import com.jonuzovic.ragesetz.core.model.Law;

public interface ILlmService {

	public String askAbout(String userQuestion, String relevantContext);
	
	public String explainLaw(Law law);
	
}
