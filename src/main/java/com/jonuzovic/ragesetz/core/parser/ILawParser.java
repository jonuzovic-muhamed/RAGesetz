package com.jonuzovic.ragesetz.core.parser;

import java.util.List;

import com.jonuzovic.ragesetz.core.model.Law;

public interface ILawParser {
	
	void downloadLawsFromSources();

	List<Law> parseDownloadedLaws();
	
}
