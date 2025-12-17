package com.jonuzovic.ragesetz.api.advice;

import com.jonuzovic.ragesetz.api.dto.ErrorResponseDto;
import com.jonuzovic.ragesetz.core.exception.IrrelevantQuestionException;
import com.jonuzovic.ragesetz.core.exception.LlmResponseParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error during request processing.")
                .message("An unexpected error occurred, the error details was written to log.")
                .build();
        log.error(Arrays.toString(ex.getStackTrace()));
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LlmResponseParsingException.class)
    public ResponseEntity<ErrorResponseDto> handleLlmResponseParsingException(LlmResponseParsingException ex) {
        String message = "An error occurred when serializing the AI response to entity! The user question was " + ex.getUserQuestion();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Error serializing AI response!")
                .message(message)
                .build();
        log.error(Arrays.toString(ex.getException().getStackTrace()));
        log.error(ex.getException().getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IrrelevantQuestionException.class)
    public ResponseEntity<ErrorResponseDto> handleLlmResponseParsingException(IrrelevantQuestionException ex) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Question is irrelevant for German Law.")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
