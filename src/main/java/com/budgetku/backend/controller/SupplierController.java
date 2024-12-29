package com.budgetku.backend.controller;

import com.budgetku.backend.exception.SupplierValidationException;
import com.budgetku.backend.payload.request.supplier.SupplierRequest;
import com.budgetku.backend.payload.response.supplier.SupplierResponse;
import com.budgetku.backend.service.SupplierService;
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
@RequestMapping("api/v1/supplier")
@RequiredArgsConstructor
@Tag(name = "Supplier Controller", description = "Operations related to suppliers management")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Create a new supplier",
            description = "Creates a new supplier in the system after validating the supplier details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the supplier"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed"),
            @ApiResponse(responseCode = "409", description = "Supplier already exists")
    })
    @PostMapping("/create")
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody SupplierRequest supplierRequest) throws SupplierValidationException {
        return ResponseEntity.ok(supplierService.create(supplierRequest));
    }
}
