package com.budgetku.backend.service;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.model.Invoice;
import com.budgetku.backend.payload.request.invoice.InvoiceRequest;
import com.budgetku.backend.payload.response.invoice.InvoiceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface InvoiceService {

    Invoice findInvoiceEntityById(UUID id) throws InvoiceNotFoundException;

    void attachMultipartFileToInvoice(UUID id, MultipartFile file) throws InvoiceNotFoundException, FailedToUploadFileException;

    InvoiceResponse create(InvoiceRequest invoiceRequest) throws InvoiceAlreadyExistsException, MovementNotFoundException, DocumentNumberNotFoundException;
}
