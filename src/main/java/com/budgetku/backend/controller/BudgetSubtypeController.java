package com.budgetku.backend.controller;

import com.budgetku.backend.exception.BudgetExceededException;
import com.budgetku.backend.exception.BudgetSubtypeAlreadyExistsException;
import com.budgetku.backend.exception.BudgetSubtypeNotFoundException;
import com.budgetku.backend.exception.BudgetTypeNotFoundException;
import com.budgetku.backend.payload.request.budget.BudgetSubtypeRequest;
import com.budgetku.backend.payload.response.budget.BudgetSubtypeResponse;
import com.budgetku.backend.service.BudgetSubtypeService;
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
}
