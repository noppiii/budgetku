package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.mapper.UserDTOMapper;
import com.budgetku.backend.model.User;
import com.budgetku.backend.payload.request.user.SignInRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.repository.UserRepository;
import com.budgetku.backend.security.JwtService;
import com.budgetku.backend.service.AuthenticationService;
import com.budgetku.backend.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.budgetku.backend.model.enumType.UserStatus.LOGGED_IN;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialService userCredentialService;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) throws InvalidPasswordException, UserNotFoundException, EmailNotFoundException {
        User user = userCredentialService.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new EmailNotFoundException(signInRequest.getEmail()));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setStatus(LOGGED_IN);
        userCredentialService.save(user);
        return userDTOMapper.toDTO(jwtService.generateToken(user), jwtService.generateRefreshToken(user), user.getId(), user.getNif(), user.getFirstName());
    }
}
