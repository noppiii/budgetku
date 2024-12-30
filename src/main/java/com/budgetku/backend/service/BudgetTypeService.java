package com.budgetku.backend.service;

import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.CustomPageableResponse;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BudgetTypeService {

    BudgetTypeResponse createBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeAlreadyExistsException;

    void deleteBudgetType(UUID id) throws BudgetTypeNotFoundException;

    BudgetTypeResponse updateBudgetType(BudgetTypeRequest budgetTypeRequest) throws BudgetTypeNotFoundException, BudgetTypeAlreadyExistsException, BudgetSubtypeNotFoundException;

    BudgetTypeResponse findBudgetTypeDTOById(UUID id) throws BudgetTypeNotFoundException, BudgetSubtypeNotFoundException;

    Page<BudgetTypeResponse> findAllBudgetTypes(CustomPageableResponse customPageableResponse) throws JsonProcessingException;
}
