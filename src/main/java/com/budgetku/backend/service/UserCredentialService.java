package com.budgetku.backend.service;

import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.model.User;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;

import java.util.Optional;

public interface UserCredentialService {
    Optional<User> findByNif(String nif);
    AuthenticationResponse register(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException;
}
