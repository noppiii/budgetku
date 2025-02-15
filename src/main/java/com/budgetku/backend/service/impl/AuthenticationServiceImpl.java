package com.budgetku.backend.service.impl;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.NifNotFoundException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.mapper.UserMapper;
import com.budgetku.backend.model.User;
import com.budgetku.backend.payload.request.user.SignInRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.security.JwtService;
import com.budgetku.backend.service.AuthenticationService;
import com.budgetku.backend.service.UserCredentialService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.budgetku.backend.model.enumType.UserStatus.LOGGED_IN;
import static com.budgetku.backend.model.enumType.UserStatus.LOGGED_OUT;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;
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
        return userMapper.toDTO(jwtService.generateToken(user), jwtService.generateRefreshToken(user), user.getId(), user.getNif(), user.getFirstName());
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request) throws IOException, UserNotFoundException, NifNotFoundException {
        String refreshToken = request.getHeader("Authorization").replace("Bearer ", "");
        String nif = jwtService.extractNif(refreshToken);

        User user = userCredentialService.findByNif(nif).orElseThrow(() -> new NifNotFoundException(nif));
        return userMapper.toDTOWithoutUserID(jwtService.generateToken(user), refreshToken);
    }

    @Override
    public void signOut(HttpServletRequest request) throws NifNotFoundException {
        String nif = jwtService.extractNif(request.getHeader("Authorization")).replace("Bearer ", "");
        User user = userCredentialService.findByNif(nif).orElseThrow(() -> new NifNotFoundException(nif));
        user.setStatus(LOGGED_OUT);
        userCredentialService.save(user);
        SecurityContextHolder.clearContext();
    }
}
