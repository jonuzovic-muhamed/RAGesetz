package com.jonuzovic.ragesetz.api.mapper.impl;

import org.springframework.stereotype.Component;

import com.jonuzovic.ragesetz.api.dto.LawDto;
import com.jonuzovic.ragesetz.api.mapper.DtoMapper;
import com.jonuzovic.ragesetz.core.model.Law;

@Component("lawDtoMapper")
public class LawDtoMapper implements DtoMapper<Law, LawDto>{

	@Override
	public LawDto mapToDto(Law entity) {
		if (entity == null) {
			return new LawDto();
		}
		return LawDto.builder()
				.lawCode(entity.getLawCode())
				.lawSectionNumber(entity.getLawSectionNumber())
				.lawTitle(entity.getLawTitle())
				.lawContent(entity.getLawContent())
				.sourceUrl(entity.getSourceUrl())
				.build();
	}
}
