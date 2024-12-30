package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.BUDGET_TYPE_NOT_FOUND;

@Getter
public class BudgetTypeNotFoundException extends Exception {

    private UUID id;
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public BudgetTypeNotFoundException(UUID id) {
        super(BUDGET_TYPE_NOT_FOUND.getMessage(id));
        this.id = id;
        this.message = BUDGET_TYPE_NOT_FOUND.getMessage(id);
        this.status = BUDGET_TYPE_NOT_FOUND.getStatus();
        this.errorCode = BUDGET_TYPE_NOT_FOUND.getErrorCode();
    }
}
