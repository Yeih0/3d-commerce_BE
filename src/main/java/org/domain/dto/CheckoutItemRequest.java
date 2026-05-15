package org.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CheckoutItemRequest {

    @NotNull(message = "ID prodotto è obbligatorio")
    private Long productId;

    @NotNull(message = "Quantità è obbligatoria")
    @Positive(message = "Quantità deve essere positiva")
    private Integer quantity;

    private String selectedMaterial;

    private String selectedColor;

    private Boolean hasCustomization = false;

    private CustomizationDetailsRequest customizationDetails;
}