package com.jonuzovic.ragesetz.api.mapper.impl;

import com.jonuzovic.ragesetz.api.dto.AdviceResponseDto;
import com.jonuzovic.ragesetz.api.dto.LawDto;
import com.jonuzovic.ragesetz.api.mapper.DtoMapper;
import com.jonuzovic.ragesetz.core.model.Law;
import com.jonuzovic.ragesetz.core.response.AdviceResponse;

import java.util.ArrayList;
import java.util.List;

public class AdviceResponseDtoMapper implements DtoMapper<AdviceResponse, AdviceResponseDto> {
    @Override
    public AdviceResponseDto mapToDto(AdviceResponse adviceResponse) {
        if (adviceResponse == null) {
            return AdviceResponseDto.builder().build();
        }
        List<LawDto> lawDtos =  new ArrayList<>();
        for (Law law : adviceResponse.getRelevantLawsForQuestion()) {
            lawDtos.add(LawDto.builder()
                            .lawCode(law.getLawCode())
                            .lawSectionNumber(law.getLawSectionNumber())
                            .lawContent(law.getLawContent())
                            .sourceUrl(law.getSourceUrl())
                            .build());
        }
        return AdviceResponseDto.builder()
                .generatedAdvice(adviceResponse.getGeneratedAdvice())
                .relevantLawsForQuestion(lawDtos)
                .build();
    }
}
