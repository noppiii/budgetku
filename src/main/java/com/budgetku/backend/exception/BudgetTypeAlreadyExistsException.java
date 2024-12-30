package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.BUDGET_TYPE_ALREADY_EXISTS;

@Getter
public class BudgetTypeAlreadyExistsException extends Exception {

    private UUID id;
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public BudgetTypeAlreadyExistsException(UUID id, String name) {
        super(BUDGET_TYPE_ALREADY_EXISTS.getMessage(name));
        this.id = id;
        this.message = BUDGET_TYPE_ALREADY_EXISTS.getMessage(name);
        this.status = BUDGET_TYPE_ALREADY_EXISTS.getStatus();
        this.errorCode = BUDGET_TYPE_ALREADY_EXISTS.getErrorCode();
    }

    public BudgetTypeAlreadyExistsException(String name) {
        super(BUDGET_TYPE_ALREADY_EXISTS.getMessage(name));
        this.message = BUDGET_TYPE_ALREADY_EXISTS.getMessage(name);
        this.status = BUDGET_TYPE_ALREADY_EXISTS.getStatus();
        this.errorCode = BUDGET_TYPE_ALREADY_EXISTS.getErrorCode();
    }
}