package com.budgetku.backend.repository;

import com.budgetku.backend.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    boolean existsByNif(String nif);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCompanyName(String companyName);

    boolean existsByNifAndIdNot(String nif, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);

    boolean existsByCompanyNameAndIdNot(String phoneNumber, UUID id);
}
