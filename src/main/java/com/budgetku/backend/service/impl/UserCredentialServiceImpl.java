package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.mapper.UserDTOMapper;
import com.budgetku.backend.model.User;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.repository.UserRepository;
import com.budgetku.backend.security.JwtService;
import com.budgetku.backend.service.UserCredentialService;
import com.budgetku.backend.validator.UserCredentialValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;
    private final JwtService jwtService;

    @Override
    public Optional<User> findByNif(String nif) {
        return userRepository.findByNif(nif);
    }

    @Override
    public AuthenticationResponse register(UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException {
        UserCredentialValidator.validateUserCredentialsCreation(userCredentialRequest, userRepository);
        User newUser = userDTOMapper.toEntity(userCredentialRequest, passwordEncoder);
        userRepository.save(newUser);
        return userDTOMapper.toDTO(jwtService.generateToken(newUser), jwtService.generateRefreshToken(newUser), newUser.getId());
    }
}
