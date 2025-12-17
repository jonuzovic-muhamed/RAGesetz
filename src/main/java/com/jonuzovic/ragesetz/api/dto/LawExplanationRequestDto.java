package com.jonuzovic.ragesetz.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LawExplanationRequestDto {
    private String userQuestion;
    private String lawCode;
    private String lawSectionNumber;
}
