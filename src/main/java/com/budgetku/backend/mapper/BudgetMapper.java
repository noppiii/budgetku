package com.budgetku.backend.mapper;

import com.budgetku.backend.model.BudgetSubtype;
import com.budgetku.backend.model.BudgetType;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetSubtypeResponse;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    BudgetSubtype toEntity(BudgetSubtypeRequest budgetSubtypeRequest);

    BudgetType toEntity(BudgetTypeRequest budgetTypeRequest);

    @Mapping(target = "subtypes", qualifiedByName = "withoutBudgetType")
    BudgetTypeResponse toDTO(BudgetType budgetType);

    @Mapping(target = "budgetType.subtypes", ignore = true)
    BudgetSubtypeResponse toDTO(BudgetSubtype budgetSubtype);

    @Named("withoutBudgetType")
    default List<BudgetSubtypeResponse> mapSubtypesWithoutBudgetType(List<BudgetSubtype> subtypes) {
        if (subtypes == null) {
            return Collections.emptyList();
        }

        return subtypes.stream().map(
                        subtype -> {
                            BudgetSubtypeResponse dto = toDTO(subtype);
                            dto.setBudgetType(null);
                            return dto;
                        })
                .collect(Collectors.toList());

    }
}
