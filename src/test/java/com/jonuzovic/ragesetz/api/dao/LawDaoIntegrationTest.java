package com.jonuzovic.ragesetz.api.dao;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jonuzovic.ragesetz.api.converter.IVectorConverter;
import com.jonuzovic.ragesetz.api.model.Law;

@SpringBootTest
@ActiveProfiles("test")
class LawDaoIntegrationTest {
	
	private static final List<Float> EMBEDDING = new ArrayList<>(Collections.nCopies(1536, 0.1f));

	
	@Autowired
	@Qualifier("lawDao")
	IEmbeddingDataAccessObject embeddingDataAccessObject;
	
	@Autowired
	@Qualifier("lawDao")
	IDataAccessObject accessObject;

    @Autowired
    private IVectorConverter converter;

    @BeforeEach
    @AfterEach
    void setup() {
    	accessObject.deleteAll();
    }

    @Test
    void testIsTableEmptyInitially() {
        boolean empty = embeddingDataAccessObject.isTableEmpty();
        assertThat(empty).isTrue();
    }

    @Test
    void testCreateAndFindLaw() {
        Law law = new Law();
        law.setLawCode("CIV-001");
        law.setLawSectionNumber("§12");
        law.setLawTitle("Civil Protection Law");
        law.setLawContent("Content of the law...");
        law.setLawEmbedding(EMBEDDING);
        law.setSourceUrl("https://example.com/law");

        accessObject.create(law);

        List<Law> all = embeddingDataAccessObject.getAllEmbeddings();
        assertThat(all).hasSize(1);

        Optional<Law> found = accessObject.findById(all.getFirst().getId());
        assertThat(found).isPresent();
        assertThat(found.get().getLawCode()).isEqualTo("CIV-001");
    }

    @Test
    void testDeleteAll() {
    	accessObject.deleteAll();
        assertThat(embeddingDataAccessObject.isTableEmpty()).isTrue();
    }

    void testCreateFindAndDeleteByIdWork() {
        Law law = new Law();
        law.setLawCode("CIV-001");
        law.setLawSectionNumber("§12");
        law.setLawTitle("Civil Protection Law");
        law.setLawContent("Content of the law...");
        law.setLawEmbedding(EMBEDDING);
        law.setSourceUrl("https://example.com/law");
        
        Long id = accessObject.create(law);
        
        Optional<Law> found = accessObject.findById(id);
        Law foundLaw = found.orElse(null);
        assertThat(foundLaw).isNotNull();
        assertThat(foundLaw.getId()).isEqualTo(id);
        assertThat(foundLaw.getLawCode()).isEqualTo(law.getLawCode());
        assertThat(foundLaw.getLawSectionNumber()).isEqualTo(law.getLawSectionNumber());
        assertThat(foundLaw.getLawCode()).isEqualTo(law.getLawCode());


        
        Long deletedId = accessObject.deleteById(id);
        assertThat(embeddingDataAccessObject.isTableEmpty()).isTrue();
        assertThat(foundLaw.getId()).isEqualTo(deletedId);
    }
    
    @Test
    void testDeleteOlderThan() {

    	Law oldLaw = new Law();
        oldLaw.setLawCode("OLD");
        oldLaw.setLawSectionNumber("§0");
        oldLaw.setLawTitle("Old Law");
        oldLaw.setLawContent("old");
        oldLaw.setLawEmbedding(EMBEDDING);
        oldLaw.setSourceUrl("none");

        accessObject.create(oldLaw);
        assertThat(embeddingDataAccessObject.isTableEmpty()).isFalse();

        // delete older than today
        Date today = new Date(System.currentTimeMillis());
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        embeddingDataAccessObject.deleteOlderThan(tomorrow);

        assertThat(embeddingDataAccessObject.isTableEmpty()).isTrue();
    }
    
    @Test
    void testFindRelevantEmbeddingsOrdersBySimilarity() {
        List<Float> baseEmbedding = new ArrayList<>(Collections.nCopies(1536, 0.0f));
        List<Float> nearEmbedding = new ArrayList<>(Collections.nCopies(1536, 0.1f));
        List<Float> farEmbedding = new ArrayList<>(Collections.nCopies(1536, 5.0f));

        accessObject.create(buildLaw("LAW-1", baseEmbedding));
        accessObject.create(buildLaw("LAW-2", nearEmbedding));
        accessObject.create(buildLaw("LAW-3", farEmbedding));

        List<Float> queryEmbedding = new ArrayList<>(Collections.nCopies(1536, 0.05f));
        List<Law> results = embeddingDataAccessObject.findRelevantEmbeddings(queryEmbedding, 3);

        assertThat(results).hasSize(3);
        assertThat(results.get(0).getLawCode()).isEqualTo("LAW-1");
        assertThat(results.get(2).getLawCode()).isEqualTo("LAW-3");
    }

    private Law buildLaw(String code, List<Float> embedding) {
        Law law = new Law();
        law.setLawCode(code);
        law.setLawSectionNumber("§" + code);
        law.setLawTitle("Title " + code);
        law.setLawContent("Content of " + code);
        law.setLawEmbedding(embedding);
        law.setSourceUrl("https://ragesetzt.com/" + code);
        return law;
    }
}
