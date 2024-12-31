package com.budgetku.backend.util;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.model.BudgetSubtype;
import com.budgetku.backend.model.BudgetType;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.repository.BudgetSubtypeRepository;
import com.budgetku.backend.service.BudgetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetUtils {

    public void checkBudgetExceeded(BudgetType budgetType, BudgetSubtypeRequest budgetSubtypeRequest, BudgetSubtypeRepository budgetSubtypeRepository, BudgetSubtype existingBudgetSubtype) throws BudgetExceededException {
        double totalSpentForType = budgetSubtypeRepository.findByBudgetType(budgetType).stream()
                .mapToDouble(BudgetSubtype::getAvailableFunds).sum();

        if (existingBudgetSubtype != null) {
            totalSpentForType -= existingBudgetSubtype.getAvailableFunds();
        }

        if (totalSpentForType + budgetSubtypeRequest.getAvailableFunds() > budgetType.getAvailableFunds()) {
            throw new BudgetExceededException(budgetSubtypeRequest.getAvailableFunds(), totalSpentForType);
        }
    }

    public void handleDeleteBudgetSubtypeAvailableFunds(BudgetSubtype budgetSubtype, BudgetTypeService budgetTypeService, BudgetSubtypeRepository budgetSubtypeRepository) {
        BudgetType budgetType = budgetSubtype.getBudgetType();
        budgetType.setAvailableFunds(budgetType.getAvailableFunds() - budgetSubtype.getAvailableFunds());
        budgetTypeService.save(budgetType);
        budgetSubtypeRepository.deleteById(budgetSubtype.getId());
    }
}
