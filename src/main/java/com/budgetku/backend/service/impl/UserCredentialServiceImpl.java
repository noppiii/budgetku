package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.mapper.UserMapper;
import com.budgetku.backend.model.User;
import com.budgetku.backend.model.enumType.UserStatus;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.request.user.UserDeleteRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.repository.PasswordResetTokenRepository;
import com.budgetku.backend.repository.UserRepository;
import com.budgetku.backend.security.JwtService;
import com.budgetku.backend.service.UserCredentialService;
import com.budgetku.backend.validator.UserCredentialValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public Optional<User> findByNif(String nif) {
        return userRepository.findByNif(nif);
    }

    @Override
    public AuthenticationResponse register(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException {
        UserCredentialValidator.validateUserCredentialsCreation(userCredentialRequest, userRepository);
        User newUser = userMapper.toEntity(userCredentialRequest, passwordEncoder);
        userRepository.save(newUser);
        return userMapper.toDTO(jwtService.generateToken(newUser), jwtService.generateRefreshToken(newUser), newUser.getId());
    }

    @Override
    public UserCredentialRequest update(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException, InvalidPasswordException, UserNotFoundException {
        User existingUser = userRepository.findById(userCredentialRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(userCredentialRequest.getId()));

        if (!passwordEncoder.matches(userCredentialRequest.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        UserCredentialValidator.validateUserCredentialUpdate(userCredentialRequest, userRepository);

        if (userCredentialRequest.getNewPassword() != null) {
            userMapper.updateFromDTO(userCredentialRequest, existingUser, passwordEncoder);
        } else {
            userMapper.updateFromDTO(userCredentialRequest, existingUser);
        }
        return userMapper.toDTO(userRepository.save(existingUser));
    }

    @Override
    public void delete(UserDeleteRequest deleteRequest) throws InvalidPasswordException, UserNotFoundException {
        User existingUser = userRepository.findById(deleteRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(deleteRequest.getId()));

        if (!passwordEncoder.matches(deleteRequest.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        userRepository.delete(existingUser);
    }

    @Override
    public UserStatus getUserStatus(String nif) {
        return userRepository.findByNif(nif).map(User::getStatus) .orElse(UserStatus.LOGGED_OUT);
    }

    @Override
    public UserCredentialRequest findById(UUID id) throws UserNotFoundException {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
