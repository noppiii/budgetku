package com.budgetku.backend.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Base Data Transfer Object providing a unique identifier for derived DTOs.")
public abstract class AbstractResponse {

    @Schema(description = "Unique correlation ID to trace and associate requests/responses.", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", required = true)
    private UUID correlationId;

    @Schema(description = "Unique identifier (UUID) for each entity instance", example = "e12a567d-32f8-4e9a-9073-6781f9d5e423")
    private UUID id;

    @Schema(description = "Version number for optimistic locking and concurrent updates management.", example = "1", required = true)
    private int version;
}