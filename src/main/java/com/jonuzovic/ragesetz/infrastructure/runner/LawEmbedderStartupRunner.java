package com.jonuzovic.ragesetz.infrastructure.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jonuzovic.ragesetz.core.embedder.ILawEmbedder;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.parser.ILawParser;

@Component
public class LawEmbedderStartupRunner implements CommandLineRunner {
	
	private final Logger log = LoggerFactory.getLogger(LawEmbedderStartupRunner.class);
	
	private ILawEmbedder lawEmbedder;
	private ILawParser lawParser;
	
	public LawEmbedderStartupRunner(
			@Qualifier("lawEmbdedderService") ILawEmbedder lawEmbedder,
			@Qualifier("htmlLawParser") ILawParser lawParser) {
		super();
		this.lawEmbedder = lawEmbedder;
		this.lawParser = lawParser;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started embedding process!");
		long startTime = System.currentTimeMillis();
		lawParser.downloadLawsFromSources();
		List<Law> laws = lawParser.parseDownloadedLaws();
		lawEmbedder.embedLaws(laws);
		long endTime = System.currentTimeMillis();
		long[] runtimeDuration = calculateRuntime(startTime, endTime);
		log.info("Finished embedding process in {}h {}m {}s {}ms", runtimeDuration[0], runtimeDuration[1], runtimeDuration[2], runtimeDuration[3]);
	}
	
	private long[] calculateRuntime(long startTime, long endTime) {
		long duration = endTime - startTime;
		long hours   = (duration / (1000 * 60 * 60)) % 24;
		long minutes = (duration / (1000 * 60)) % 60;
		long seconds = (duration / 1000) % 60;
		long millis  = duration % 1000;
		return new long[]{hours, minutes, seconds, millis};
	}
}
