package com.budgetku.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.budgetku.backend.exception.ErrorMessage.BUDGET_EXCEEDED;

@Getter
public class BudgetExceededException extends Exception {

    private UUID id;
    private final String message;
    private final HttpStatus status;
    private final String errorCode;

    public BudgetExceededException(UUID id, Double totalValue, Double totalSpent) {
        super(BUDGET_EXCEEDED.getMessage(totalValue, totalSpent));
        this.id = id;
        this.message = BUDGET_EXCEEDED.getMessage(totalValue, totalSpent);
        this.status = BUDGET_EXCEEDED.getStatus();
        this.errorCode = BUDGET_EXCEEDED.getErrorCode();
    }

    public BudgetExceededException(Double totalValue, Double totalSpent) {
        super(BUDGET_EXCEEDED.getMessage(totalValue, totalSpent));
        this.message = BUDGET_EXCEEDED.getMessage(totalValue, totalSpent);
        this.status = BUDGET_EXCEEDED.getStatus();
        this.errorCode = BUDGET_EXCEEDED.getErrorCode();
    }
}
