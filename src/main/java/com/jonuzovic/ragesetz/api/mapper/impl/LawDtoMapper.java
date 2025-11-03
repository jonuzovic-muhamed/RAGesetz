package com.jonuzovic.ragesetz.api.mapper.impl;

import com.jonuzovic.ragesetz.api.dto.LawDto;
import com.jonuzovic.ragesetz.api.mapper.DtoMapper;
import com.jonuzovic.ragesetz.core.model.Law;

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
