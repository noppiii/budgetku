package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.mapper.InvoiceMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.budgetku.backend.model.enumType.MovementStatus.*;

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
    private final InvoiceMapper invoiceMapper;

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

    @Override
    public Movement getMovementEntityById(UUID id) throws MovementNotFoundException {
        return findById(id);
    }

    @Override
    public Movement getMovementByDocumentNumber(String movementDocumentNumber) throws DocumentNumberNotFoundException {
        Movement movement = movementRepository.findByDocumentNumber(movementDocumentNumber)
                .orElseThrow(() -> new DocumentNumberNotFoundException(movementDocumentNumber));

        MovementResponse movementResponse = movementMapper.toDTO(movement);
        movementResponse.setInvoice(invoiceMapper.toRequest(movement.getInvoice()));
        return movement;
    }

    @Override
    @Transactional
    public MovementResponse update(MovementRequest movementRequest) throws MovementNotFoundException, SupplierNotFoundException, BudgetSubtypeNotFoundException, MovementAlreadyExistsException, MovementValidationException, InvoiceNotFoundException, BudgetExceededException, BudgetTypeNotFoundException {
        Movement previousMovement = findById(movementRequest.getId());
        movementValidator.validateMovementUpdate(movementRequest, movementRepository, supplierService, invoiceService);
        movementUtil.calculateIvaAndTotal(movementRequest);

        if (movementRequest.getStatus().equals(SUCCEEDED)) {
            movementUtil.adjustBudgetAmounts(budgetSubtypeService, budgetTypeService, previousMovement, movementRequest);
        } else if (movementRequest.getStatus().equals(CANCELED) || movementRequest.getStatus().equals(REFUNDED) && previousMovement.getStatus().equals(SUCCEEDED)) {
            movementUtil.removeMovementValueFromBudget(previousMovement, budgetSubtypeService, budgetTypeService);
        }

        Movement existingMovement = movementMapper.toEntity(movementRequest);
        existingMovement.setSupplier(supplierService.findSupplierEntityById(movementRequest.getSupplierId()));
        existingMovement.setInvoice(invoiceService.findInvoiceEntityById(movementRequest.getInvoiceId()));
        movementUtil.setBudget(existingMovement, movementRequest, budgetSubtypeService, budgetTypeService);
        MovementResponse exisitingMovementResponse = movementMapper.toDTO(movementRepository.save(existingMovement));
        return exisitingMovementResponse;
    }

    private Movement findById(UUID id) throws MovementNotFoundException {
        Optional<Movement> movement = movementRepository.findById(id);

        if (movement.isPresent()) {
            return movement.get();
        }

        throw new MovementNotFoundException(id);
    }
}
