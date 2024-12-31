package com.budgetku.backend.controller;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetSubtypeResponse;
import com.budgetku.backend.service.BudgetSubtypeService;
import com.budgetku.backend.util.PageableUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budget/subtype")
@RequiredArgsConstructor
@Tag(name = "Budget Subtype Controller", description = "Operations related to budget subtypes")
public class BudgetSubtypeController {

    private final BudgetSubtypeService budgetSubtypeService;

    @Operation(summary = "Create a new budget subtype",
            description = "Creates a new budget subtype for a given budget type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new budget subtype"),
            @ApiResponse(responseCode = "404", description = "Budget type not found"),
            @ApiResponse(responseCode = "409", description = "Budget subtype already exists"),
            @ApiResponse(responseCode = "400", description = "Budget exceeded")
    })
    @PostMapping("/create")
    public ResponseEntity<BudgetSubtypeResponse> addSubtype(@Valid @RequestBody BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetTypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetSubtypeNotFoundException, BudgetExceededException {
        return ResponseEntity.ok(budgetSubtypeService.addSubtypeToBudget(budgetSubtypeRequest));
    }

    @Operation(summary = "Update an existing budget subtype",
            description = "Updates the details of an existing budget subtype.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the budget subtype"),
            @ApiResponse(responseCode = "404", description = "Budget subtype not found"),
            @ApiResponse(responseCode = "409", description = "Budget subtype already exists"),
            @ApiResponse(responseCode = "400", description = "Budget exceeded")
    })
    @PutMapping("/update")
    public ResponseEntity<BudgetSubtypeResponse> updateSubtype(@Valid @RequestBody BudgetSubtypeRequest budgetSubtypeRequest) throws BudgetSubtypeNotFoundException, BudgetSubtypeAlreadyExistsException, BudgetExceededException {
        return ResponseEntity.ok(budgetSubtypeService.updateBudgetSubtype(budgetSubtypeRequest));
    }

    @Operation(summary = "Delete a budget subtype",
            description = "Deletes a budget subtype by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the budget subtype"),
            @ApiResponse(responseCode = "404", description = "Budget subtype not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubtype(@PathVariable UUID id) throws BudgetSubtypeNotFoundException {
        budgetSubtypeService.deleteBudgetSubtype(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a budget subtype by ID",
            description = "Fetches a budget subtype by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the budget subtype"),
            @ApiResponse(responseCode = "404", description = "Budget subtype not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BudgetSubtypeResponse> findSubtypeById(@PathVariable UUID id) throws BudgetSubtypeNotFoundException {
        return ResponseEntity.ok(budgetSubtypeService.findBudgetSubtypeById(id));
    }

    @Operation(summary = "Get all budget subtypes",
            description = "Fetches all budget subtypes, paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the list of budget subtypes"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<Page<BudgetSubtypeResponse>> findAllSubtypes(@PageableDefault(size = 10, page = 0) Pageable pageable) throws JsonProcessingException {
        return ResponseEntity.ok(budgetSubtypeService.findAllBudgetSubtypes(PageableUtils.convertToCustomPageable(pageable)));
    }
}
