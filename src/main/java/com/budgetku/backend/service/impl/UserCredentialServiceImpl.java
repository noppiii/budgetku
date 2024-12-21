package com.budgetku.backend.service.impl;

import com.budgetku.backend.model.User;
import com.budgetku.backend.repository.UserRepository;
import com.budgetku.backend.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByNif(String nif) {
        return userRepository.findByNif(nif);
    }
}
