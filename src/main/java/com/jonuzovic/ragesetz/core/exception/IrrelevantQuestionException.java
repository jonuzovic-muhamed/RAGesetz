package com.jonuzovic.ragesetz.core.exception;

import lombok.Getter;

@Getter
public class IrrelevantQuestionException extends RuntimeException {
    private final String userQuestion;
    private final String message;

    public IrrelevantQuestionException(String userQuestion, String message) {
        super("The asked question is irrelevant for law context.");
        this.userQuestion = userQuestion;
        this.message = message;
    }
}
