package com.budgetku.backend.validator;

import com.budgetku.backend.exception.MovementValidationException;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.repository.MovementRepository;
import com.budgetku.backend.service.InvoiceService;
import com.budgetku.backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class MovementValidator {

    public void validateMovement(MovementRequest movementRequest, MovementRepository repository, SupplierService supplierService, InvoiceService invoiceService) throws MovementValidationException {

        List<String> errorMessages = new ArrayList<>();

        log.info("Checking for existing document number: {}", movementRequest.getDocumentNumber());

        if (repository.existsByDocumentNumber(movementRequest.getDocumentNumber())) {
            log.error("Validation failed: Document number already exists. Document number: {}", movementRequest.getDocumentNumber());
            errorMessages.add("a movement already exists with document number: " + movementRequest.getDocumentNumber());
        }

        if (movementRequest.getInvoiceId() != null) {
            log.info("Checking for existing movement with invoice ID: {}", movementRequest.getInvoiceId());

            if (repository.existsByInvoiceId(movementRequest.getInvoiceId())) {
                log.error("Validation failed: Movement already exists with invoice ID {}", movementRequest.getInvoiceId());
                errorMessages.add("a movement already exists with invoice ID: " + movementRequest.getInvoiceId());
            }
        }

        if (!supplierService.existsById(movementRequest.getSupplierId())) {
            log.error("Validation failed: Supplier with ID {} not found.", movementRequest.getSupplierId());
            errorMessages.add("supplier with ID " + movementRequest.getSupplierId() + " not found.");
        }

        if (!errorMessages.isEmpty()) {
            log.error("Movement validation failed with errors: {}", errorMessages);
            throw new MovementValidationException(errorMessages);
        }
    }

    public void validateMovementValues(MovementRequest movementRequest) throws MovementValidationException {
        List<String> errorMessages = new ArrayList<>();

        if (movementRequest.getValueWithoutIva() <= 0) {
            errorMessages.add("value without IVA must be greater than 0");
        }

        if (movementRequest.getIvaRate() < 0) {
            errorMessages.add("IVA rate must be greater than or equal to 0 if provided");
        }

        if (!errorMessages.isEmpty()) {
            log.error("Movement validation failed with errors: {}", errorMessages);
            throw new MovementValidationException(errorMessages);
        }
    }
}
