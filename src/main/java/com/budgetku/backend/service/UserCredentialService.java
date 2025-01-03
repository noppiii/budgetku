package com.budgetku.backend.service;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.model.User;
import com.budgetku.backend.model.enumType.UserStatus;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.request.user.UserDeleteRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialService {

    Optional<User> findByNif(String nif);

    AuthenticationResponse register(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException;

    UserCredentialRequest update(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException, InvalidPasswordException, UserNotFoundException;

    void delete(UserDeleteRequest deleteRequest) throws InvalidPasswordException, UserNotFoundException;

    UserStatus getUserStatus(String nif);

    UserCredentialRequest findById(UUID id) throws UserNotFoundException;

    Optional<User> findByEmail(String email);

    void save(User user);
}
