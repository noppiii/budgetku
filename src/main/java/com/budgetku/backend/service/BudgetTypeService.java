package com.budgetku.backend.service;

import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;

import java.util.UUID;

public interface BudgetTypeService {

    BudgetTypeResponse createBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeAlreadyExistsException;

    void deleteBudgetType(UUID id) throws BudgetTypeNotFoundException;
}
