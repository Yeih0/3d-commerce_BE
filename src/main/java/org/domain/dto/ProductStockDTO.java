package org.domain.dto;

import lombok.Data;

@Data
public class ProductStockDTO {
    private Long id;
    private String material;
    private String color;
    private Integer quantity;
}