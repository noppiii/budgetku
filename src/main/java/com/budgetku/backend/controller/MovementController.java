package com.budgetku.backend.controller;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.payload.request.invoice.MovementRequest;
import com.budgetku.backend.payload.response.invoice.MovementResponse;
import com.budgetku.backend.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movement")
@RequiredArgsConstructor
@Tag(name = "Movement Controller", description = "Operations related to financial movements")
public class MovementController {

    private final MovementService movementService;

    @Operation(summary = "Create a new movement",
            description = "Creates a new financial movement. The movement should be validated with all relevant details, including supplier, invoice, and budget subtype.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the movement"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid data"),
            @ApiResponse(responseCode = "409", description = "Movement already exists"),
            @ApiResponse(responseCode = "404", description = "Supplier or Invoice not found"),
            @ApiResponse(responseCode = "406", description = "Budget exceeded")
    })
    @PostMapping("/create")
    public ResponseEntity<MovementResponse> createMovement(@Valid @RequestBody MovementRequest movementRequest) throws BudgetSubtypeNotFoundException, SupplierNotFoundException, MovementAlreadyExistsException, MovementValidationException, InvoiceNotFoundException, BudgetExceededException, BudgetTypeNotFoundException {
        return ResponseEntity.ok(movementService.create(movementRequest));
    }
}
