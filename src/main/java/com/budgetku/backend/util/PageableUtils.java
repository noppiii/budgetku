package com.budgetku.backend.util;

import com.budgetku.backend.enumerated.SortDirection;
import com.budgetku.backend.payload.response.CustomPageResponse;
import com.budgetku.backend.payload.response.CustomPageableResponse;
import com.budgetku.backend.payload.response.SortPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public class PageableUtils {

    public static CustomPageableResponse convertToCustomPageable(Pageable pageable) {
        String sortField = pageable.getSort().isSorted()
                ? pageable.getSort().iterator().next().getProperty()
                : null;

        String sortOrder = pageable.getSort().isSorted()
                ? pageable.getSort().iterator().next().getDirection().name()
                : "ASC";

        SortPage sortPage = new SortPage();
        sortPage.setSorted(pageable.getSort().isSorted());
        sortPage.setUnsorted(pageable.getSort().isUnsorted());
        sortPage.setEmpty(pageable.getSort().isEmpty());
        sortPage.setField(sortField);
        sortPage.setDirection(SortDirection.valueOf(sortOrder));

        return new CustomPageableResponse(UUID.randomUUID(), pageable.getPageNumber(), pageable.getPageSize(), (int) pageable.getOffset(), pageable.isPaged(), pageable.isUnpaged(), sortPage);
    }

    public static Pageable convertToPageable(CustomPageableResponse customPageableResponse) {
        Sort.Direction direction = "desc".equalsIgnoreCase(customPageableResponse.getSortOrder())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        String sortField = customPageableResponse.getSortField() != null ? customPageableResponse.getSortField() : "id";

        return PageRequest.of(
                customPageableResponse.getPageNumber(),
                customPageableResponse.getPageSize(),
                Sort.by(direction, sortField)
        );
    }

    public static <T> CustomPageResponse<T> buildCustomPageResponse(CustomPageableResponse customPageableResponse, List<T> dtoList, Page<?> entityPage) {

        CustomPageableResponse pageableResponse = new CustomPageableResponse(
                customPageableResponse.getCorrelationId(),
                customPageableResponse.getPageNumber(),
                customPageableResponse.getPageSize(),
                customPageableResponse.getOffset(),
                customPageableResponse.isPaged(),
                customPageableResponse.isUnpaged(),
                customPageableResponse.getSort()
        );

        return new CustomPageResponse<>(
                dtoList,
                customPageableResponse.getPageSize(),
                entityPage.getTotalElements(),
                pageableResponse,
                entityPage.isLast(),
                entityPage.isFirst(),
                entityPage.getNumberOfElements(),
                entityPage.isEmpty()
        );
    }
}
