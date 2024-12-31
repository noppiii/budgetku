package com.budgetku.backend.service;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetSubtypeResponse;

import java.util.UUID;

public interface BudgetSubtypeService {

    BudgetSubtypeResponse addSubtypeToBudget(BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetTypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetSubtypeNotFoundException, BudgetExceededException;

    BudgetSubtypeResponse updateBudgetSubtype(BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetSubtypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetExceededException;

    void deleteBudgetSubtype(UUID subtypeId) throws BudgetSubtypeNotFoundException;

    BudgetSubtypeResponse findBudgetSubtypeById(UUID subtypeId) throws BudgetSubtypeNotFoundException;

}
