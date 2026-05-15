package org.domain.dto;

import lombok.Data;

@Data
public class CustomizationDetailsRequest {

    private String description;

    private Integer estimatedDays;

    private String font;

    private String customerPhone;
}