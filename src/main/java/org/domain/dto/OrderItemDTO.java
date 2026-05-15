package org.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String selectedMaterial;
    private String selectedColor;
    private Boolean hasCustomization;
    private CustomizationRequestDTO customizationRequest;
}