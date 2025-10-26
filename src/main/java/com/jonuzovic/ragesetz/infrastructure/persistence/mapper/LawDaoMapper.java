package com.jonuzovic.ragesetz.infrastructure.persistence.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jonuzovic.ragesetz.core.converter.IVectorConverter;
import com.jonuzovic.ragesetz.core.model.Law;

@Component("lawDaoMapper")
public class LawDaoMapper implements RowMapper<Law> {
	
	private final IVectorConverter converter;
	
	public LawDaoMapper(IVectorConverter converter) {
		super();
		this.converter = converter;
	}

	@Override
	public Law mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Law.builder()
                .id(rs.getLong("law_id"))
                .lawCode(rs.getString("law_code"))
                .lawSectionNumber(rs.getString("law_section_number"))
                .lawTitle(rs.getString("law_title"))
                .lawContent(rs.getString("law_content"))
                .lawEmbedding(converter.convertToEntityAttribute(rs.getString("law_embedding")))
                .sourceUrl(rs.getString("source_url"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
	}
}
