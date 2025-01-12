package com.budgetku.backend.controller;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.payload.request.invoice.InvoiceRequest;
import com.budgetku.backend.payload.response.invoice.InvoiceResponse;
import com.budgetku.backend.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
@Tag(name = "Invoice Controller", description = "Operations related to Invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "Upload a file to an invoice",
            description = "Attach a multipart file to an existing invoice.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "File successfully uploaded"),
            @ApiResponse(responseCode = "404", description = "Invoice not found"),
            @ApiResponse(responseCode = "400", description = "Failed to upload the file")
    })
    @PostMapping("/attach-multipart-file/{invoiceId}")
    public ResponseEntity<InvoiceResponse> uploadFileToInvoice(@PathVariable UUID invoiceId, @RequestParam("file") MultipartFile file) throws InvoiceNotFoundException, FailedToUploadFileException {
        invoiceService.attachMultipartFileToInvoice(invoiceId, file);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new invoice",
            description = "Creates a new invoice linked to a movement, if applicable.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new invoice"),
            @ApiResponse(responseCode = "404", description = "Movement not found"),
            @ApiResponse(responseCode = "409", description = "Invoice already exists")
    })
    @PostMapping("/create")
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) throws InvoiceAlreadyExistsException, MovementNotFoundException, DocumentNumberNotFoundException {
        return ResponseEntity.ok(invoiceService.create(invoiceRequest));
    }
}
