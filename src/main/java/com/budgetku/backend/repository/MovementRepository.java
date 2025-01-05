package com.budgetku.backend.repository;

import com.budgetku.backend.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByInvoiceId(UUID invoiceId);
}
