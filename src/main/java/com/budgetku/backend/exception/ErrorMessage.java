package com.budgetku.backend.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ErrorMessage {

    NIF_NOT_FOUND("NIF_NOT_FOUND", "NIF not found: %s", NOT_FOUND),;

    private final String errorCode;
    private final String message;
    private final HttpStatus status;

    ErrorMessage(String errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}