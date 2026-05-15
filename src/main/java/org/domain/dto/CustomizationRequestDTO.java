package org.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomizationRequestDTO {

    private Long id;
    private String description;
    private Integer estimatedDays;
    private String font;
    private String customerPhone;
    private String status;
    private LocalDateTime createdAt;

    // Info ordine associato
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private String productName;
}