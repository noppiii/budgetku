package com.budgetku.backend.service;

import com.budgetku.backend.exception.FailedToUploadFileException;
import com.budgetku.backend.exception.InvoiceNotFoundException;
import com.budgetku.backend.model.Invoice;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface InvoiceService {

    Invoice findInvoiceEntityById(UUID id) throws InvoiceNotFoundException;

    void attachMultipartFileToInvoice(UUID id, MultipartFile file) throws InvoiceNotFoundException, FailedToUploadFileException;
}
