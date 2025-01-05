package com.budgetku.backend.service;

import com.budgetku.backend.exception.InvoiceNotFoundException;
import com.budgetku.backend.model.Invoice;

import java.util.UUID;

public interface InvoiceService {

    Invoice findInvoiceEntityById(UUID id) throws InvoiceNotFoundException;
}
