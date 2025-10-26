package com.jonuzovic.ragesetz.infrastructure.persistence.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jonuzovic.ragesetz.core.converter.IVectorConverter;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.repository.ICrudRepository;
import com.jonuzovic.ragesetz.core.repository.IEmbeddingRepository;

@Repository("lawDao")
public class LawDao implements IEmbeddingRepository, ICrudRepository {

	private final JdbcTemplate jdbcTemplate;
	private final IVectorConverter converter;
	private final RowMapper<Law> mapper;
	
	public LawDao(JdbcTemplate jdbcTemplate, IVectorConverter converter, RowMapper<Law> mapper) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.converter = converter;
		this.mapper = mapper;
	}

	@Override
	public Long create(Law law) {
		String sql = """
                INSERT INTO embeddings.law (law_code, law_section_number, law_title, law_content, law_embedding, source_url)
                VALUES (?, ?, ?, ?, CAST(? AS vector), ?)
                RETURNING law_id;
                """;
		
		return jdbcTemplate.queryForObject(sql, Long.class,
                law.getLawCode(),
                law.getLawSectionNumber(),
                law.getLawTitle(),
                law.getLawContent(),
                converter.convertToDatabaseColumn(law.getLawEmbedding()),
                law.getSourceUrl()
                );
	}

	@Override
	public Optional<Law> findById(Long id) {
		String sql = """
                SELECT * FROM embeddings.law WHERE law_id = ?;
                """;
		List<Law> results = jdbcTemplate.query(sql, mapper, id);
		return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
	}
	
	public Optional<Law> findByLawCode(String lawCode) {
		String sql = """
                SELECT * FROM embeddings.law WHERE law_code = ?;
                """;
		List<Law> results = jdbcTemplate.query(sql, mapper, lawCode);
		return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
	}

	@Override
	public void update(Law law) {
        jdbcTemplate.update(                                                                                                                                                                                                    
                """                                                                                                                                                                                                             
                UPDATE embeddings.law                                                                                                                                                              
                SET law_code = ?, law_section_number = ?, law_title = ?, law_content = ?, law_embedding = CAST(? AS vector), source_url = ?, created_at = ?                                                                                                                
                WHERE law_id = ?                                                                                                                                                                                                
                """,                                                                                                                                                                                                        
                law.getLawCode(),
                law.getLawSectionNumber(),
                law.getLawTitle(),
                law.getLawContent(),
                converter.convertToDatabaseColumn(law.getLawEmbedding()),
                law.getSourceUrl(),
                law.getCreatedAt(),
                law.getId()
        );  
	}

	@Override
	public Long deleteById(Long id) {
		return jdbcTemplate.queryForObject("DELETE FROM embeddings.law WHERE law_id = ? RETURNING law_id;", Long.class, id);
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("TRUNCATE TABLE embeddings.law");
	}

	@Override
	public void deleteOlderThan(Date date) {
		jdbcTemplate.update("DELETE FROM embeddings.law WHERE created_at <= ?", date);
	}

	@Override
	public boolean isTableEmpty() {
        Integer rowCount = jdbcTemplate.queryForObject("SELECT count(*) FROM embeddings.law", Integer.class);                                                                                          
        return rowCount == null || rowCount == 0; 
	}

	@Override
	public List<Law> getAllEmbeddings() {
		String sql = """
                SELECT law_id, law_code, law_section_number, law_title, law_content, law_embedding, source_url, created_at
                FROM embeddings.law
                """;
		return jdbcTemplate.query(sql, mapper);
	}

	@Override
	public List<Law> findRelevantEmbeddings(List<Float> embedding, Integer limit) {
        String sql = """
                SELECT law_id, law_code, law_section_number, law_title, law_content, law_embedding, source_url, created_at
                FROM embeddings.law
                ORDER BY law_embedding <-> CAST(? AS vector)
                LIMIT ?;
                """;

    return jdbcTemplate.query(
            sql,
            mapper,
            converter.convertToDatabaseColumn(embedding),
            limit );
    
	}
}
