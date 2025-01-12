package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.*;
import com.budgetku.backend.mapper.InvoiceMapper;
import com.budgetku.backend.model.Invoice;
import com.budgetku.backend.model.Movement;
import com.budgetku.backend.payload.request.invoice.InvoiceRequest;
import com.budgetku.backend.payload.response.invoice.InvoiceResponse;
import com.budgetku.backend.repository.InvoiceRepository;
import com.budgetku.backend.service.FileService;
import com.budgetku.backend.service.InvoiceService;
import com.budgetku.backend.service.MovementService;
import com.budgetku.backend.util.Base64DecodedMultipartFile;
import com.budgetku.backend.util.InvoiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final MovementService movementService;
    private final InvoiceMapper invoiceMapper;

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

    @Override
    @Transactional
    public InvoiceResponse create(InvoiceRequest invoiceRequest) throws InvoiceAlreadyExistsException, MovementNotFoundException, DocumentNumberNotFoundException {
        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);
        Movement movement = null;

        if (invoiceRequest.getMovementId() != null) {
            movement = movementService.getMovementEntityById(invoiceRequest.getMovementId());
            invoice.setMovement(movement);
        } else if (invoiceRequest.getMovementDocumentNumber() != null) {
            movement = movementService.getMovementByDocumentNumber(invoiceRequest.getMovementDocumentNumber());
            invoice.setMovement(movement);
        }

        Invoice savedInvoice = invoiceRepository.save(invoice);

        if (movement != null) {
            movement.setInvoice(savedInvoice);
        }

        InvoiceResponse savedInvoiceResponse = invoiceMapper.toDTO(savedInvoice);
        savedInvoiceResponse.setCorrelationId(invoiceRequest.getCorrelationId());
        return savedInvoiceResponse;
    }

    private Invoice findById(UUID id) throws InvoiceNotFoundException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isPresent()) {
            return invoice.get();
        }

        throw new InvoiceNotFoundException(id);
    }
}
