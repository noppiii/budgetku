package com.budgetku.backend.controller;

import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(
            summary = "Delete a Budget Type",
            description = "Deletes a Budget Type by its unique ID. Throws a 404 error if the Budget Type is not found."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the Budget Type"),
            @ApiResponse(responseCode = "404", description = "Budget Type not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBudgetType(@PathVariable UUID id) throws BudgetTypeNotFoundException {
        budgetTypeService.deleteBudgetType(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update an existing Budget Type",
            description = "Updates an existing Budget Type. If the Budget Type does not exist or a conflict occurs, the operation will throw appropriate errors."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the Budget Type"),
            @ApiResponse(responseCode = "404", description = "Budget Type not found"),
            @ApiResponse(responseCode = "409", description = "Budget Type already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid Budget Type data")
    })
    @PutMapping("/update")
    public ResponseEntity<BudgetTypeResponse> updateBudgetType(@Valid @RequestBody BudgetTypeRequest budgetTypeRequest) throws BudgetTypeNotFoundException, BudgetTypeAlreadyExistsException, BudgetSubtypeNotFoundException {
        return ResponseEntity.ok(budgetTypeService.updateBudgetType(budgetTypeRequest));
    }
}
