package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.BUDGET_SUBTYPE_NOT_FOUND;

@Getter
public class BudgetSubtypeNotFoundException extends Exception {

    private UUID id;
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public BudgetSubtypeNotFoundException(UUID id) {
        super(BUDGET_SUBTYPE_NOT_FOUND.getMessage(id));
        this.id = id;
        this.message = BUDGET_SUBTYPE_NOT_FOUND.getMessage(id);
        this.status = BUDGET_SUBTYPE_NOT_FOUND.getStatus();
        this.errorCode = BUDGET_SUBTYPE_NOT_FOUND.getErrorCode();
    }
}
