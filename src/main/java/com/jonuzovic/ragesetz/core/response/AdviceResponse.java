package com.jonuzovic.ragesetz.core.response;

import com.jonuzovic.ragesetz.core.model.Law;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdviceResponse {
    private String generatedAdvice;
    private List<Law> relevantLawsForQuestion;
}
