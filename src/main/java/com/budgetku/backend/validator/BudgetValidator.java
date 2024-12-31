package com.budgetku.backend.validator;

import com.budgetku.backend.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.repository.BudgetSubtypeRepository;
import com.budgetku.backend.repository.BudgetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetValidator {

    public void checkForExistingBudgetType(BudgetTypeRequest budgetTypeRequest, BudgetTypeRepository budgetTypeRepository) throws BudgetTypeAlreadyExistsException {
        if (budgetTypeRepository.findByName(budgetTypeRequest.getName()).isPresent()) {
            throw new BudgetTypeAlreadyExistsException(budgetTypeRequest.getName());
        }
    }

    public void checkForExistingBudgetTypeUpdate(BudgetTypeRequest budgetTypeDTO, BudgetTypeRepository budgetTypeRepository) throws BudgetTypeAlreadyExistsException {
        if (budgetTypeRepository.findByNameAndIdNot(budgetTypeDTO.getName(), budgetTypeDTO.getId()).isPresent()) {
            throw new BudgetTypeAlreadyExistsException(budgetTypeDTO.getName());
        }
    }

    public void checkForExistingBudgetSubtype(BudgetSubtypeRequest budgetSubtypeRequest, BudgetSubtypeRepository budgetSubtypeRepository) throws BudgetSubtypeAlreadyExistsException {
        if (budgetSubtypeRepository.findByName(budgetSubtypeRequest.getName()).isPresent()) {
            throw new BudgetSubtypeAlreadyExistsException(budgetSubtypeRequest.getName());
        }
    }

    public void checkForExistingBudgetSubtypeUpdate(BudgetSubtypeRequest budgetSubtypeRequest, BudgetSubtypeRepository budgetSubtypeRepository) throws BudgetSubtypeAlreadyExistsException {
        if (budgetSubtypeRepository.findByNameAndIdNot(budgetSubtypeRequest.getName(), budgetSubtypeRequest.getId()).isPresent()) {
            throw new BudgetSubtypeAlreadyExistsException(budgetSubtypeRequest.getName());
        }
    }
}
