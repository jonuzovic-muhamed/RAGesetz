package com.jonuzovic.ragesetz.api.mapper.impl;

import com.jonuzovic.ragesetz.api.dto.LawExplanationResponseDto;
import com.jonuzovic.ragesetz.api.mapper.DtoMapper;
import com.jonuzovic.ragesetz.core.response.LawExplanationResponse;

public class LawExplanationResponseDtoMapper implements DtoMapper<LawExplanationResponse, LawExplanationResponseDto> {

    @Override
    public LawExplanationResponseDto mapToDto(LawExplanationResponse lawExplanationResponse) {
        if (lawExplanationResponse == null) {
            return LawExplanationResponseDto.builder().build();
        }
        return LawExplanationResponseDto.builder()
                .law_code(lawExplanationResponse.getLaw_code())
                .lawSectionNumber(lawExplanationResponse.getLawSectionNumber())
                .lawTitle(lawExplanationResponse.getLawTitle())
                .lawContent(lawExplanationResponse.getLawContent())
                .generatedExplanation(lawExplanationResponse.getGeneratedExplanation())
                .build();
    }
}
