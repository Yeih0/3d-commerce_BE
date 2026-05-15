package org.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCustomizationStatusRequest {

    @NotBlank(message = "Stato è obbligatorio")
    private String status; // PENDING, CONFIRMED, APPROVED, REJECTED, COMPLETED
}