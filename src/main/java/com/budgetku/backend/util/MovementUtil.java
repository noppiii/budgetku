package com.budgetku.backend.util;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.exception.MovementValidationException;
import com.budgetku.backend.model.BudgetSubtype;
import com.budgetku.backend.model.BudgetType;
import com.budgetku.backend.model.Movement;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.service.BudgetSubtypeService;
import com.budgetku.backend.service.BudgetTypeService;
import com.budgetku.backend.validator.MovementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovementUtil {

    private final MovementValidator movementValidator;

    public void calculateIvaAndTotal(MovementRequest movementRequest) throws MovementValidationException {
        movementValidator.validateMovementValues(movementRequest);
        Double ivaValue = (movementRequest.getValueWithoutIva() * (movementRequest.getIvaRate() != null ? movementRequest.getIvaRate() : 0)) / 100;
        Double totalValue = movementRequest.getValueWithoutIva() + ivaValue;
        movementRequest.setIvaValue(ivaValue);
        movementRequest.setTotalValue(totalValue);
    }

    public void setBudget(Movement movement, MovementRequest movementRequest, BudgetSubtypeService budgetSubtypeService, BudgetTypeService budgetTypeService) throws BudgetSubtypeNotFoundException, BudgetTypeNotFoundException {
        if (movementRequest.getBudgetSubtypeId() != null) {
            BudgetSubtype budgetSubtype = budgetSubtypeService.findBudgetSubtypeEntityById(movementRequest.getBudgetSubtypeId());
            movement.setBudgetSubtype(budgetSubtype);
            movement.setBudgetType(null);
        } else if (movementRequest.getBudgetTypeId() != null) {
            BudgetType budgetType = budgetTypeService.findBudgetTypeEntityById(movementRequest.getBudgetTypeId());
            movement.setBudgetType(budgetType);
            movement.setBudgetSubtype(null);
        }
    }

    public void updateSpentAmounts(MovementRequest movementRequest, BudgetSubtypeService budgetSubtypeService, BudgetTypeService budgetTypeService, Movement movement, Double totalValue) throws BudgetExceededException {
        if (movementRequest.getBudgetSubtype() != null) {
            BudgetSubtype budgetSubtype = movement.getBudgetSubtype();
            BudgetType type = budgetSubtype.getBudgetType();

            if (totalValue > budgetSubtype.getAvailableFunds()) {
                throw new BudgetExceededException(totalValue, budgetSubtype.getAvailableFunds());
            }

            budgetSubtype.setAvailableFunds(budgetSubtype.getAvailableFunds() - totalValue);
            budgetSubtypeService.save(budgetSubtype);

            if (type != null) {
                type.setAvailableFunds(type.getAvailableFunds() - totalValue);
                budgetTypeService.save(type);
            }
        } else if (movement.getBudgetType() != null) {
            BudgetType type = movement.getBudgetType();

            if (totalValue > type.getAvailableFunds()) {
                throw new BudgetExceededException(totalValue, type.getAvailableFunds());
            }

            type.setAvailableFunds(type.getAvailableFunds() - totalValue);
            budgetTypeService.save(type);
        }
    }

    public void adjustBudgetAmounts(BudgetSubtypeService budgetSubtypeService, BudgetTypeService budgetTypeService, Movement oldMovement, MovementRequest newMovementRequest) throws BudgetExceededException {
        double oldTotalValue = oldMovement.getTotalValue();
        double newTotalValue = newMovementRequest.getTotalValue();
        double valueDifference = newTotalValue - oldTotalValue;

        if (oldMovement.getBudgetSubtype() != null) {
            BudgetSubtype subtype = oldMovement.getBudgetSubtype();
            BudgetType type = subtype.getBudgetType();

            if (valueDifference > subtype.getAvailableFunds()) {
                throw new BudgetExceededException(valueDifference, subtype.getAvailableFunds());
            }

            subtype.setAvailableFunds(subtype.getAvailableFunds() - oldTotalValue + newTotalValue);
            budgetSubtypeService.save(subtype);

            if (type != null) {
                type.setAvailableFunds(type.getAvailableFunds() - oldTotalValue + newTotalValue);
                budgetTypeService.save(type);
            }
        } else if (oldMovement.getBudgetType() != null) {
            BudgetType type = oldMovement.getBudgetType();

            if (valueDifference > type.getAvailableFunds()) {
                throw new BudgetExceededException(valueDifference, type.getAvailableFunds());
            }

            type.setAvailableFunds(type.getAvailableFunds() - oldTotalValue + newTotalValue);
            budgetTypeService.save(type);
        }
    }

    public void removeMovementValueFromBudget(Movement movement, BudgetSubtypeService budgetSubtypeService, BudgetTypeService budgetTypeService) {
        Double totalValue = movement.getTotalValue();

        if (movement.getBudgetSubtype() != null) {
            BudgetSubtype subtype = movement.getBudgetSubtype();
            subtype.setAvailableFunds(subtype.getAvailableFunds() + totalValue);
            budgetSubtypeService.save(subtype);

            if (subtype.getBudgetType() != null) {
                BudgetType type = subtype.getBudgetType();
                type.setAvailableFunds(type.getAvailableFunds() + totalValue);
                budgetTypeService.save(type);
            }
        } else if (movement.getBudgetType() != null) {
            BudgetType type = movement.getBudgetType();
            type.setAvailableFunds(type.getAvailableFunds() + totalValue);
            budgetTypeService.save(type);
        }
    }
}
