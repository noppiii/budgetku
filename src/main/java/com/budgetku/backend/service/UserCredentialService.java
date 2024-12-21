package com.budgetku.backend.service;

import com.budgetku.backend.model.User;

import java.util.Optional;

public interface UserCredentialService {
    Optional<User> findByNif(String nif);
}
