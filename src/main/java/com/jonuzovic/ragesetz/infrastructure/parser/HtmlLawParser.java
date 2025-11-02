package com.jonuzovic.ragesetz.infrastructure.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.parser.ILawParser;
import com.jonuzovic.ragesetz.infrastructure.config.LawSourcesProperties;

@Service("htmlLawParser")
public class HtmlLawParser implements ILawParser {
	
	private final Logger log = LoggerFactory.getLogger(HtmlLawParser.class);

    private final LawSourcesProperties lawSources;

	public HtmlLawParser(LawSourcesProperties lawSources) {
		this.lawSources = lawSources;
	}

    private Path dataDir = Paths.get("src/main/resources/data");
    
    public void downloadLawsFromSources() {
    	
    		try {
	    		if (!Files.exists(dataDir)) {
	    			Files.createDirectories(dataDir);
	    			log.info("Created the /data directory to store the html files.");
	        }
    		} catch (IOException e) {
    			log.error("Error occured trying to create the /data directory to store the html files.");
    		}

        if (lawSources.getLaws() == null || lawSources.getLaws().isEmpty()) {
            log.warn("No law sources configured in lawsources.yml — nothing to download.");
            return;
        }
    	
        for (LawSourcesProperties.LawSource lawSource : lawSources.getLaws()) {
        		String code = lawSource.getCode();
            String url = lawSource.getUrl();
			try {
	            Document doc = Jsoup.connect(url).get();
	            Files.writeString(dataDir.resolve(code.toLowerCase() + ".html"), doc.outerHtml());
	            log.info("Downloaded html data file for {}", code);
			} catch(IOException e) {
				log.error("Error occured during download of html file {}", lawSource.getUrl());
			}
        }
        
    }

	@Override
	public List<Law> parseDownloadedLaws() {
		try {
			List<Law> allLaws = new ArrayList<>();

	        for (LawSourcesProperties.LawSource lawSource : lawSources.getLaws()) {
	            String code = lawSource.getCode();
	            Path file = dataDir.resolve(code.toLowerCase() + ".html");
	            if (!Files.exists(file)) {
	                log.error("File not found for {} — skipping.", code);
	                continue;
	            }
	            allLaws.addAll(parseFile(file, code, lawSource.getUrl()));
	        }
	        return allLaws;
		} catch(IOException e) {
			return List.of();
		}
	}
	
	private List<Law> parseFile(Path filePath, String lawCode, String sourceUrl) throws IOException {
	    List<Law> results = new ArrayList<>();
	    Document doc = Jsoup.parse(filePath.toFile(), "UTF-8");

	    Elements sections = doc.select("div.jnnorm");

	    for (Element section : sections) {
	        try {
	            Element header = section.selectFirst("div.jnheader");
	            if (header == null) continue;

	            String sectionNumber = header.selectFirst("span.jnenbez") != null
	                    ? header.selectFirst("span.jnenbez").text().trim()
	                    : "";

	            String title = header.selectFirst("span.jnentitel") != null
	                    ? header.selectFirst("span.jnentitel").text().trim()
	                    : "";

	            Elements paragraphs = section.select("div.jnhtml .jurAbsatz");
	            StringBuilder content = new StringBuilder();
	            for (Element p : paragraphs) {
	                String text = p.text().trim();
	                if (!text.isEmpty()) {
	                    content.append(text).append("\n");
	                }
	            }

	            if (sectionNumber.isEmpty() && content.isEmpty()) continue;

	            Law law = Law.builder()
	                    .lawCode(lawCode)
	                    .lawSectionNumber(sectionNumber)
	                    .lawTitle(title)
	                    .lawContent(content.toString().trim())
	                    .sourceUrl(sourceUrl)
	                    .build();

	            results.add(law);
	        } catch (Exception e) {
	            log.error("Failed to parse section in {}: {}", lawCode, e.getMessage());
	        }
	    }

	    log.info("Parsed {} sections from {} ({})", results.size(), lawCode, filePath.getFileName());
	    return results;
	}

}
