package com.budgetku.backend.service;

import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;

public interface BudgetTypeService {

    BudgetTypeResponse createBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeAlreadyExistsException;
}
