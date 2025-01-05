package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.MOVEMENT_VALIDATION_ERROR;

@Getter
public class MovementValidationException extends Exception {

    private UUID id;
    private final List<String> errors;
    private final HttpStatus status;
    private final String errorCode;

    public MovementValidationException(List<String> errorMessages) {
        super(MOVEMENT_VALIDATION_ERROR.getMessage(errorMessages));
        this.errors = errorMessages;
        this.status = MOVEMENT_VALIDATION_ERROR.getStatus();
        this.errorCode = MOVEMENT_VALIDATION_ERROR.getErrorCode();
    }

    public MovementValidationException(List<String> errorMessages, UUID id) {
        super(MOVEMENT_VALIDATION_ERROR.getMessage(errorMessages));
        this.id = id;
        this.errors = errorMessages;
        this.status = MOVEMENT_VALIDATION_ERROR.getStatus();
        this.errorCode = MOVEMENT_VALIDATION_ERROR.getErrorCode();
    }
}
