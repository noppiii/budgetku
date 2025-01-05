package com.budgetku.backend.mapper;

import com.budgetku.backend.model.Movement;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.payload.response.invoice.MovementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    @Mapping(target = "budgetType.subtypes", ignore = true)
    @Mapping(target = "budgetSubtype.budgetType", ignore = true)
    @Mapping(target = "invoice.movement", ignore = true)
    MovementResponse toDTO(Movement movement);

    Movement toEntity(MovementRequest movementRequest);
}
