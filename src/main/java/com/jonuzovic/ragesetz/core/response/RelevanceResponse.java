package com.jonuzovic.ragesetz.core.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RelevanceResponse {
    Boolean isRelevant;
    String message;
}
