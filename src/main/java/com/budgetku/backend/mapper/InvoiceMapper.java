package com.budgetku.backend.mapper;

import com.budgetku.backend.model.Invoice;
import com.budgetku.backend.payload.request.invoice.InvoiceRequest;
import com.budgetku.backend.payload.response.invoice.InvoiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice toEntity(InvoiceRequest invoiceRequest);

    @Mapping(target = "movement.budgetType.subtypes", ignore = true)
    @Mapping(target = "movement.subtype.budgetType", ignore = true)
    @Mapping(target = "movement.invoice", ignore = true)
    InvoiceResponse toDTO(Invoice invoice);

    InvoiceRequest toRequest(Invoice invoice);
}
