package com.jonuzovic.ragesetz.core.response;

import com.jonuzovic.ragesetz.api.dto.LawDto;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdviceResponse {
    private String generatedAdvice;
    private List<LawDto> relevantLawsForQuestion;
}
