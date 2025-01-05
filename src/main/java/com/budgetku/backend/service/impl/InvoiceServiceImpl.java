package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.FailedToUploadFileException;
import com.budgetku.backend.exception.InvoiceNotFoundException;
import com.budgetku.backend.model.Invoice;
import com.budgetku.backend.repository.InvoiceRepository;
import com.budgetku.backend.service.FileService;
import com.budgetku.backend.service.InvoiceService;
import com.budgetku.backend.util.Base64DecodedMultipartFile;
import com.budgetku.backend.util.InvoiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final FileService fileService;

    @Override
    public Invoice findInvoiceEntityById(UUID id) throws InvoiceNotFoundException {
        return findById(id);
    }

    @Override
    public void attachMultipartFileToInvoice(UUID id, MultipartFile file) throws InvoiceNotFoundException, FailedToUploadFileException {
        Invoice invoice = findById(id);

        if (invoice.getFileKey() != null) {
            fileService.deleteObject(invoice.getFileKey());
        }

        try {
            String fileName = UUID.randomUUID() + InvoiceUtil.getFileExtensionFromContentType(Objects.requireNonNull(file.getContentType()));
            invoice.setFileKey(fileService.putObject(new Base64DecodedMultipartFile(file.getBytes(), fileName, file.getContentType())));
        } catch (IOException e) {
            throw new FailedToUploadFileException(id);
        }
        invoiceRepository.save(invoice);
    }

    private Invoice findById(UUID id) throws InvoiceNotFoundException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            return invoice.get();
        }

        throw new InvoiceNotFoundException(id);
    }
}
