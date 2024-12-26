package com.budgetku.backend.controller;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.NifNotFoundException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.payload.request.user.SignInRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @Operation(summary = "Refresh the JWT token",
            description = "Refresh the user's JWT token using the refresh token.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Parameter(description = "HTTP request to extract refresh token") HttpServletRequest request) throws IOException, UserNotFoundException, NifNotFoundException {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
