package com.budgetku.backend.service;

import com.budgetku.backend.exception.SupplierNotFoundException;
import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.CustomPageableResponse;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SupplierService {

    SupplierResponse create(SupplierRequest supplierRequest) throws SupplierValidationException;

    SupplierResponse findSupplierById(UUID id) throws SupplierNotFoundException;

    SupplierResponse update(SupplierRequest supplierRequest) throws SupplierNotFoundException, SupplierValidationException;

    void delete(UUID id) throws SupplierNotFoundException;

    Page<SupplierResponse> findAll(CustomPageableResponse customPageableResponse) throws JsonProcessingException;
}
