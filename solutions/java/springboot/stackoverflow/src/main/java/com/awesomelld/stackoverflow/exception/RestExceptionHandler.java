package com.awesomelld.stackoverflow.exception;

import com.awesomelld.stackoverflow.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, ServletWebRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidActionException.class)
    ResponseEntity<ApiErrorResponse> handleInvalidAction(InvalidActionException ex, ServletWebRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    ResponseEntity<ApiErrorResponse> handleValidation(Exception ex, ServletWebRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(StackOverflowException.class)
    ResponseEntity<ApiErrorResponse> handleDomain(StackOverflowException ex, ServletWebRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, ServletWebRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, ServletWebRequest request) {
        ApiErrorResponse body = new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message,
                request.getRequest().getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
