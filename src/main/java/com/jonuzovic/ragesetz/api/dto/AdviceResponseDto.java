package com.jonuzovic.ragesetz.api.dto;

import com.jonuzovic.ragesetz.core.model.Law;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdviceResponseDto {
    private String generatedAdvice;
    private List<LawDto> relevantLawsForQuestion;
}
