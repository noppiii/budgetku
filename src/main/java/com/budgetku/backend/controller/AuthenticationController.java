package com.budgetku.backend.controller;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.payload.request.user.SignInRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Handles user authentication, token validation, and refresh token operations.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Sign in with user credentials",
            description = "Authenticate user and return JWT token if the credentials are valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, returns JWT token"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@Valid @RequestBody @Parameter(description = "User credentials (username and password)") SignInRequest signInRequest) throws InvalidPasswordException, UserNotFoundException, EmailNotFoundException {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

}
