package com.budgetku.backend.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorMessage {

    USER_CREDENTIAL_VALIDATION_ERROR("USER_CREDENTIALS_VALIDATION_ERROR", "User operation failed due to the following error/s: %s", CONFLICT),
    NIF_NOT_FOUND("NIF_NOT_FOUND", "NIF not found: %s", NOT_FOUND),
    INVALID_PASSWORD("INVALID_PASSWORD", "This password is not valid", UNAUTHORIZED),
    USER_ID_NOT_FOUND("USER_ID_NOT_FOUND", "User ID not found: %s", NOT_FOUND),
    EMAIL_NOT_FOUND("EMAIL_NOT_FOUND", "Email not found: %s", NOT_FOUND),
    SUPPLIER_VALIDATION_ERROR("SUPPLIER_VALIDATION_ERROR", "Supplier operation failed due to the following error/s: %s", CONFLICT),
    SUPPLIER_NOT_FOUND("SUPPLIER_NOT_FOUND", "Supplier not found with ID: %s", NOT_FOUND),;

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