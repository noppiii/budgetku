package com.budgetku.backend.payload.response.budget;

import com.budgetku.backend.payload.response.AbstractResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response representing a budget subtype, containing details about the subtype and its associated budget type.")
public class BudgetSubtypeResponse extends AbstractResponse {

    @Schema(description = "Name of the budget subtype.", example = "Marketing", required = true)
    private String name;

    @Schema(description = "Available funds for this budget subtype.", example = "1200.50", nullable = true)
    private Double availableFunds;

    @Schema(description = "Description of the budget subtype.", example = "Budget allocated for marketing activities.", required = true)
    private String description;

    @Schema(description = "UUID of the associated budget type.", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
    private UUID budgetTypeId;

    @Schema(description = "Associated BudgetTypeDTO for this budget subtype. It provides additional details about the budget type.", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BudgetTypeResponse budgetType;
}
