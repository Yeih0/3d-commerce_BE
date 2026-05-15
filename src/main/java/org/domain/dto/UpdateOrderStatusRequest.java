package org.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    @NotBlank(message = "Stato è obbligatorio")
    private String status; // PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}