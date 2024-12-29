package com.budgetku.backend.controller;

import com.budgetku.backend.exception.SupplierNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(summary = "Get supplier by ID",
            description = "Fetches a supplier by its ID from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the supplier"),
            @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> findBySupplierById(@PathVariable UUID id) throws SupplierNotFoundException {
        return ResponseEntity.ok(supplierService.findSupplierById(id));
    }

    @Operation(summary = "Update an existing supplier",
            description = "Updates the details of an existing supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the supplier"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed")
    })
    @PutMapping("/update")
    public ResponseEntity<SupplierResponse> update(@Valid @RequestBody SupplierRequest supplierRequest) throws SupplierNotFoundException, SupplierValidationException {
        return ResponseEntity.ok(supplierService.update(supplierRequest));
    }

    @Operation(summary = "Delete a supplier",
            description = "Deletes a supplier by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the supplier"),
            @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws SupplierNotFoundException {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
