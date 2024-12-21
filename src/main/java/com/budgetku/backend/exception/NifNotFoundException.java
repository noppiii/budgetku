package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.budgetku.backend.exception.ErrorMessage.NIF_NOT_FOUND;

@Getter
public class NifNotFoundException extends Exception {
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public NifNotFoundException(String nif) {
        super(NIF_NOT_FOUND.getMessage(nif));
        this.message = NIF_NOT_FOUND.getMessage(nif);
        this.status = NIF_NOT_FOUND.getStatus();
        this.errorCode = NIF_NOT_FOUND.getErrorCode();
    }
}
