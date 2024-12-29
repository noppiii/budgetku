package com.budgetku.backend.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response representing the authentication response, including the JWT token, refresh token and user ID.")
public class AuthenticationResponse {

    @Schema(description = "JWT token used for authenticating API requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Refresh token used for obtaining a new JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "Unique identifier for the user", example = "e12a567d-32f8-4e9a-9073-6781f9d5e423")
    private UUID id;

    @Schema(description = "Tax Identification Number (NIF) of the user", example = "123456789", required = true)
    private String nif;

    @Schema(description = "First name of the user", example = "John", required = true)
    private String firstName;
}
