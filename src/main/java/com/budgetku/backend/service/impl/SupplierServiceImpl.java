package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.SupplierNotFoundException;
import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.mapper.SupplierMapper;
import com.budgetku.backend.model.Supplier;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.CustomPageableResponse;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;
import com.budgetku.backend.repository.SupplierRepository;
import com.budgetku.backend.service.SupplierService;
import com.budgetku.backend.util.PageableUtils;
import com.budgetku.backend.validator.SupplierValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public SupplierResponse findSupplierById(UUID id) throws SupplierNotFoundException {
        SupplierResponse supplierResponse = supplierMapper.toDTO(findById(id));
        return supplierResponse;
    }

    @Override
    public SupplierResponse update(SupplierRequest supplierRequest) throws SupplierNotFoundException, SupplierValidationException {
        findById(supplierRequest.getId());
        supplierValidator.validateSupplierUpdate(supplierRequest, supplierRepository);
        Supplier existingSupplier = supplierMapper.toEntity(supplierRequest);
        SupplierResponse updatedSupplierResponse = supplierMapper.toDTO(supplierRepository.save(existingSupplier));
        return updatedSupplierResponse;
    }

    @Override
    public void delete(UUID id) throws SupplierNotFoundException {
        if (supplierRepository.existsById(id)) {
            supplierRepository.delete(findById(id));
            return;
        }

        throw new SupplierNotFoundException(id);
    }

    @Override
    public Page<SupplierResponse> findAll(CustomPageableResponse customPageableResponse) throws JsonProcessingException {
        Page<Supplier> supplierPage = supplierRepository.findAll(PageableUtils.convertToPageable(customPageableResponse));
        return supplierPage.map(supplierMapper::toDTO);
    }

    private Supplier findById(UUID id) throws SupplierNotFoundException {
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (supplier.isPresent()) {
            return supplier.get();
        }

        throw new SupplierNotFoundException(id);
    }
}
