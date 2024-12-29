package com.budgetku.backend.validator;

import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupplierValidator {

    public void validateSupplierCreation(SupplierRequest supplierRequest, SupplierRepository repository) throws SupplierValidationException {
        List<String> errorMessages = new ArrayList<>();
        validateNifForExistingSupplier(supplierRequest, repository, errorMessages);
        validateEmailForExistingSupplier(supplierRequest, repository, errorMessages);
        validatePhoneNumberForExistingSupplier(supplierRequest, repository, errorMessages);
        validateCompanyNameForExistingSupplier(supplierRequest, repository, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new SupplierValidationException(errorMessages);
        }
    }

    private static void validateNifForExistingSupplier(SupplierRequest supplierRequest, SupplierRepository repository, List<String> errorMessages) {
        log.info("Checking for existing NIF: {}", supplierRequest.getNif());

        if (repository.existsByNif(supplierRequest.getNif())) {
            errorMessages.add("NIF already exists: " + supplierRequest.getNif());
            log.error("Validation failed: NIF already exists. NIF: {}", supplierRequest.getNif());
        }
    }

    private static void validateEmailForExistingSupplier(SupplierRequest supplierRequest, SupplierRepository repository, List<String> errorMessages) {
        log.info("Checking for existing email: {}", supplierRequest.getEmail());

        if (repository.existsByEmail(supplierRequest.getEmail())) {
            errorMessages.add("Email already exists: " + supplierRequest.getEmail());
            log.error("Validation failed: Email already exists. Email: {}", supplierRequest.getEmail());
        }
    }

    private static void validatePhoneNumberForExistingSupplier(SupplierRequest supplierRequest, SupplierRepository repository, List<String> errorMessages) {
        log.info("Checking for existing phone number: {}", supplierRequest.getPhoneNumber());

        if (repository.existsByPhoneNumber(supplierRequest.getPhoneNumber())) {
            errorMessages.add("This phone number already exists: " + supplierRequest.getPhoneNumber());
            log.error("Validation failed: Phone number already exists. Phone number: {}", supplierRequest.getPhoneNumber());
        }
    }

    private static void validateCompanyNameForExistingSupplier(SupplierRequest supplierRequest, SupplierRepository repository, List<String> errorMessages) {
        log.info("Checking for existing company name: {}", supplierRequest.getCompanyName());

        if (repository.existsByCompanyName(supplierRequest.getCompanyName())) {
            errorMessages.add("This company name already exists: " + supplierRequest.getCompanyName());
            log.error("Validation failed: Company name already exists. Company name: {}", supplierRequest.getCompanyName());
        }
    }
}
