package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.mapper.MovementMapper;
import com.budgetku.backend.model.Movement;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.payload.response.invoice.MovementResponse;
import com.budgetku.backend.repository.MovementRepository;
import com.budgetku.backend.service.*;
import com.budgetku.backend.util.MovementUtil;
import com.budgetku.backend.validator.MovementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.budgetku.backend.model.enumType.MovementStatus.SUCCEEDED;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementValidator movementValidator;
    private final MovementRepository movementRepository;
    private final SupplierService supplierService;
    private final InvoiceService invoiceService;
    private final MovementUtil movementUtil;
    private final MovementMapper movementMapper;
    private final BudgetSubtypeService budgetSubtypeService;
    private final BudgetTypeService budgetTypeService;

    @Override
    public MovementResponse create(MovementRequest movementRequest) throws BudgetSubtypeNotFoundException, SupplierNotFoundException, MovementAlreadyExistsException, MovementValidationException, InvoiceNotFoundException, BudgetExceededException, BudgetTypeNotFoundException {
        movementValidator.validateMovement(movementRequest, movementRepository, supplierService, invoiceService);
        movementUtil.calculateIvaAndTotal(movementRequest);
        Movement movement = movementMapper.toEntity(movementRequest);
        movement.setSupplier(supplierService.findSupplierEntityById(movementRequest.getInvoiceId()));

        if (movementRequest.getInvoiceId() != null) {
            movement.setInvoice(invoiceService.findInvoiceEntityById(movementRequest.getInvoiceId()));
        }

        movementUtil.setBudget(movement, movementRequest, budgetSubtypeService, budgetTypeService);

        if (movement.getStatus().equals(SUCCEEDED)) {
            movementUtil.updateSpentAmounts(movementRequest, budgetSubtypeService, budgetTypeService, movement, movement.getTotalValue());
        }

        Movement savedMovement = movementRepository.save(movement);
        MovementResponse savedMovementResponse = movementMapper.toDTO(savedMovement);
        savedMovementResponse.setCorrelationId(movementRequest.getCorrelationId());
        return savedMovementResponse;
    }
}
