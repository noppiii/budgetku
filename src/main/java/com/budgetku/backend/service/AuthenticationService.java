package com.budgetku.backend.service;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.payload.request.user.SignInRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse signIn(SignInRequest signInRequest) throws InvalidPasswordException, UserNotFoundException, EmailNotFoundException;
}
