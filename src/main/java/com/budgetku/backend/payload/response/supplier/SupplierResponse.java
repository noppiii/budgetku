package com.budgetku.backend.payload.response.supplier;

import com.budgetku.backend.payload.response.AbstractResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response representing a supplier, containing details about the company, responsible person, and contact information.")
public class SupplierResponse extends AbstractResponse {

    @Schema(description = "The name of the supplier company.", example = "Tech Solutions Ltd.", required = true)
    private String companyName;

    @Schema(description = "The name of the person responsible for the supplier.", example = "John Doe", required = true)
    private String responsibleName;

    @Schema(description = "The NIF (Tax Identification Number) of the supplier.", example = "123456789", required = true)
    private String nif;

    @Schema(description = "The phone number of the supplier.", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "The email address of the supplier.", example = "supplier@example.com")
    private String email;
}