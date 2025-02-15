package com.budgetku.backend.service;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.model.Movement;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.payload.response.invoice.MovementResponse;

import java.util.UUID;

public interface MovementService {

    MovementResponse create(MovementRequest movementRequest) throws BudgetSubtypeNotFoundException, SupplierNotFoundException, MovementAlreadyExistsException, MovementValidationException, InvoiceNotFoundException, BudgetExceededException, BudgetTypeNotFoundException;

    Movement getMovementEntityById(UUID id) throws MovementNotFoundException;

    Movement getMovementByDocumentNumber(String movementDocumentNumber) throws DocumentNumberNotFoundException;

    MovementResponse update(MovementRequest movementRequest) throws MovementNotFoundException, SupplierNotFoundException, BudgetSubtypeNotFoundException, MovementAlreadyExistsException, MovementValidationException, InvoiceNotFoundException, BudgetExceededException, BudgetTypeNotFoundException;
}
