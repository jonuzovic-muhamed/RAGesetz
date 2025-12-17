package com.jonuzovic.ragesetz.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LawExplanationResponseDto {
    private String law_code;
    private String lawSectionNumber;
    private String lawTitle;
    private String lawContent;
    private String generatedExplanation;
}
