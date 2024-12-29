package com.budgetku.backend.mapper;

import com.budgetku.backend.model.Supplier;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SupplierRequest supplierRequest);

    SupplierResponse toDTO(Supplier supplier);

}

