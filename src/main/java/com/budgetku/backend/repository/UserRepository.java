package com.budgetku.backend.repository;

import com.budgetku.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByNif(String nif);
    boolean existsByNif(String nif);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
