package com.budgetku.backend.validator;

import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
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
}
