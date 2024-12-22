package com.budgetku.backend.validator;

import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserCredentialValidator {

    public static void validateUserCredentialsCreation(UserCredentialRequest userCredentialRequest, UserRepository repository) throws UserCredentialValidationException {
        List<String> errorMessages = new ArrayList<>();
        validateNifForExistingUserCreation(userCredentialRequest, repository, errorMessages);
        validateEmailForExistingUserCreation(userCredentialRequest, repository, errorMessages);
        validatePhoneNumberForExistingUserCreation(userCredentialRequest, repository, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new UserCredentialValidationException(errorMessages);
        }
    }

    private static void validateNifForExistingUserCreation(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing NIF (creation): {}", userCredentialRequest.getNif());

        if (repository.existsByNif(userCredentialRequest.getNif())) {
            errorMessages.add("NIF already exists: " + userCredentialRequest.getNif());
            log.error("Validation failed: NIF already exists. NIF: {}", userCredentialRequest.getNif());
        }
    }

    private static void validateEmailForExistingUserCreation(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing email (creation): {}", userCredentialRequest.getEmail());

        if (repository.existsByEmail(userCredentialRequest.getEmail())) {
            errorMessages.add("Email already exists: " + userCredentialRequest.getEmail());
            log.error("Validation failed: Email already exists. Email: {}", userCredentialRequest.getEmail());
        }
    }

    private static void validatePhoneNumberForExistingUserCreation(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing phone number (creation): {}", userCredentialRequest.getPhoneNumber());

        if (repository.existsByPhoneNumber(userCredentialRequest.getPhoneNumber())) {
            errorMessages.add("This phone number already exists: " + userCredentialRequest.getPhoneNumber());
            log.error("Validation failed: Phone number already exists. Phone number: {}", userCredentialRequest.getPhoneNumber());
        }
    }

    public static void validateUserCredentialUpdate(UserCredentialRequest userCredentialRequest, UserRepository repository) throws UserCredentialValidationException {
        List<String> errorMessages = new ArrayList<>();
        validateNifForExistingUserUpdate(userCredentialRequest, repository, errorMessages);
        validateEmailForExistingUserUpdate(userCredentialRequest, repository, errorMessages);
        validatePhoneNumberForExistingUserUpdate(userCredentialRequest, repository, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new UserCredentialValidationException(errorMessages);
        }
    }

    private static void validateNifForExistingUserUpdate(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing NIF (update): {}", userCredentialRequest.getNif());

        if (repository.existsByNifAndIdNot(userCredentialRequest.getNif(), userCredentialRequest.getId())) {
            errorMessages.add("NIF already exists: " + userCredentialRequest.getNif());
            log.error("Validation failed: NIF already exists. NIF: {}", userCredentialRequest.getNif());
        }
    }

    private static void validateEmailForExistingUserUpdate(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing email (update): {}", userCredentialRequest.getEmail());

        if (repository.existsByEmailAndIdNot(userCredentialRequest.getEmail(), userCredentialRequest.getId())) {
            errorMessages.add("Email already exists: " + userCredentialRequest.getEmail());
            log.error("Validation failed: Email already exists. Email: {}", userCredentialRequest.getEmail());
        }
    }

    private static void validatePhoneNumberForExistingUserUpdate(UserCredentialRequest userCredentialRequest, UserRepository repository, List<String> errorMessages) {
        log.info("Checking for existing phone number (update): {}", userCredentialRequest.getPhoneNumber());

        if (repository.existsByPhoneNumberAndIdNot(userCredentialRequest.getPhoneNumber(), userCredentialRequest.getId())) {
            errorMessages.add("This phone number already exists: " + userCredentialRequest.getPhoneNumber());
            log.error("Validation failed: Phone number already exists. Phone number: {}", userCredentialRequest.getPhoneNumber());
        }
    }

}
