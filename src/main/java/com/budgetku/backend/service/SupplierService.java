package com.budgetku.backend.service;

import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;

public interface SupplierService {

    SupplierResponse create(SupplierRequest supplierRequest) throws SupplierValidationException;
}
