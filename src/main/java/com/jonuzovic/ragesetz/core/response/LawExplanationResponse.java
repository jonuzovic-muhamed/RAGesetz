package com.jonuzovic.ragesetz.core.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LawExplanationResponse {
    private String law_code;
    private String lawSectionNumber;
    private String lawTitle;
    private String lawContent;
    private String generatedExplanation;
}
