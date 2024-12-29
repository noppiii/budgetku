package com.budgetku.backend.service;

import com.budgetku.backend.exception.SupplierNotFoundException;
import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;

import java.util.UUID;

public interface SupplierService {

    SupplierResponse create(SupplierRequest supplierRequest) throws SupplierValidationException;

    SupplierResponse findSupplierById(UUID id) throws SupplierNotFoundException;

    SupplierResponse update(SupplierRequest supplierRequest) throws SupplierNotFoundException, SupplierValidationException;
}
