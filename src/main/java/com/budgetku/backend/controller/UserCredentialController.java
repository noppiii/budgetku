package com.budgetku.backend.controller;

import com.budgetku.backend.exception.EmailNotFoundException;
import com.budgetku.backend.exception.InvalidPasswordException;
import com.budgetku.backend.exception.UserCredentialValidationException;
import com.budgetku.backend.exception.UserNotFoundException;
import com.budgetku.backend.model.enumType.UserStatus;
import com.budgetku.backend.payload.request.user.UserCredentialRequest;
import com.budgetku.backend.payload.request.user.UserDeleteRequest;
import com.budgetku.backend.payload.response.user.AuthenticationResponse;
import com.budgetku.backend.service.UserCredentialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-credentials")
@RequiredArgsConstructor
@Tag(name = "User Credentials Controller", description = "APIs for managing user credentials, including registration, updating, and deletion.")
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    @Operation(summary = "Register new user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid user data")
            })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException {
        return ResponseEntity.ok(userCredentialService.register(userCredentialRequest));
    }

    @Operation(summary = "Update existing user credentials",
            description = "Update the user credentials such as username, password, etc.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User credentials updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or validation failed"),
            @ApiResponse(responseCode = "404", description = "User ID not found")
    })
    @PutMapping("/update")
    public ResponseEntity<UserCredentialRequest> update(@Valid @RequestBody @Parameter(description = "Updated user credentials data") UserCredentialRequest userCredentialRequest) throws UserCredentialValidationException, InvalidPasswordException, UserNotFoundException {
        return ResponseEntity.ok(userCredentialService.update(userCredentialRequest));
    }

    @Operation(summary = "Delete a user account",
            description = "Delete an existing user account by providing the user's credentials.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or password mismatch"),
            @ApiResponse(responseCode = "404", description = "User ID not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestBody @Parameter(description = "User credentials to delete the account") UserDeleteRequest deleteRequest) throws InvalidPasswordException, UserNotFoundException {
        userCredentialService.delete(deleteRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Retrieve the user's status (logged in or logged out)",
            description = "This method checks the current status of the user, whether they are logged in or logged out based on their nif.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User status fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @GetMapping("/status")
    public ResponseEntity<UserStatus> getUserStatus(@RequestParam("nif") @Parameter(description = "The user's NIF to check their current login status", required = true) String nif) {
        return ResponseEntity.ok(userCredentialService.getUserStatus(nif));
    }

    @Operation(
            summary = "Retrieve user credentials by ID",
            description = "Fetch user credentials using the unique user ID provided as a query parameter.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User credentials retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/get-user-by-id")
    public ResponseEntity<UserCredentialRequest> getUserById(@RequestParam @Parameter(description = "Unique user ID as a query parameter", required = true) UUID id) throws UserNotFoundException {
        return ResponseEntity.ok(userCredentialService.findById(id));
    }
}
