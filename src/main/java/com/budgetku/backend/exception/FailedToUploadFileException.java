package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.FAILED_TO_UPLOAD_FILE;

@Getter
public class FailedToUploadFileException extends Exception {

    private UUID id;
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public FailedToUploadFileException(UUID id) {
        super(FAILED_TO_UPLOAD_FILE.getMessage(id));
        this.id = id;
        this.message = FAILED_TO_UPLOAD_FILE.getMessage(id);
        this.status = FAILED_TO_UPLOAD_FILE.getStatus();
        this.errorCode = FAILED_TO_UPLOAD_FILE.getErrorCode();
    }
}