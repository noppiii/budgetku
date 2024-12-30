package com.budgetku.backend.controller;

import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.payload.request.budget.BudgetTypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetTypeResponse;
import com.budgetku.backend.service.BudgetTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/budget/type")
@RequiredArgsConstructor
@Tag(name = "Budget Type Controller", description = "Operations related to Budget Types")
public class BudgetTypeController {

    private final BudgetTypeService budgetTypeService;

    @Operation(
            summary = "Create a new Budget Type",
            description = "Creates a new Budget Type. This operation will check for existing budget types and throw an error if a duplicate is found."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new Budget Type"),
            @ApiResponse(responseCode = "409", description = "Budget Type already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid Budget Type data")
    })
    @PostMapping("/create")
    public ResponseEntity<BudgetTypeResponse> createBudgetType(@Valid @RequestBody BudgetTypeRequest budgetTypeRequest) throws BudgetTypeAlreadyExistsException {
        return ResponseEntity.ok(budgetTypeService.createBudgetType(budgetTypeRequest));
    }
}
