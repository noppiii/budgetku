package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.InvoiceNotFoundException;
import com.budgetku.backend.model.Invoice;
import com.budgetku.backend.repository.InvoiceRepository;
import com.budgetku.backend.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice findInvoiceEntityById(UUID id) throws InvoiceNotFoundException {
        return findById(id);
    }

    private Invoice findById(UUID id) throws InvoiceNotFoundException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            return invoice.get();
        }

        throw new InvoiceNotFoundException(id);
    }
}
