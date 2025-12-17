package com.jonuzovic.ragesetz.core.exception;

import lombok.Getter;

@Getter
public class LlmResponseParsingException extends RuntimeException {
    private final String userQuestion;
    private final Exception exception;

    public LlmResponseParsingException(Exception ex, String userQuestion) {
        super(ex.getMessage(), ex);
        this.userQuestion = userQuestion;
        this.exception = ex;
    }
}
