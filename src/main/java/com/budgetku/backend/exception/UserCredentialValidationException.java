package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.budgetku.backend.exception.ErrorMessage.USER_CREDENTIAL_VALIDATION_ERROR;

@Getter
public class UserCredentialValidationException extends Exception {
    private final List<String> errors;
    private final HttpStatus status;
    private final String errorCode;

    public UserCredentialValidationException(List<String> errorMessages) {
        super(USER_CREDENTIAL_VALIDATION_ERROR.getMessage(errorMessages));
        this.errors = errorMessages;
        this.status = USER_CREDENTIAL_VALIDATION_ERROR.getStatus();
        this.errorCode = USER_CREDENTIAL_VALIDATION_ERROR.getErrorCode();
    }
}
