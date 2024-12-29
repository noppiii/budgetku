package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.mapper.SupplierMapper;
import com.budgetku.backend.model.Supplier;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;
import com.budgetku.backend.repository.SupplierRepository;
import com.budgetku.backend.service.SupplierService;
import com.budgetku.backend.validator.SupplierValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierValidator supplierValidator;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponse create(SupplierRequest supplierRequest) throws SupplierValidationException {
        supplierValidator.validateSupplierCreation(supplierRequest, supplierRepository);
        Supplier savedSupplier = supplierRepository.save(supplierMapper.toEntity(supplierRequest));
        SupplierResponse savedSupplierResponse = supplierMapper.toDTO(savedSupplier);
        savedSupplierResponse.setCorrelationId(supplierRequest.getCorrelationId());

        return savedSupplierResponse;
    }
}
