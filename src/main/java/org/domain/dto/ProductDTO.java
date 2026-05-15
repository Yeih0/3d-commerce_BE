package org.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String dimensions;
    private Integer processingDays;
    private Integer shippingDays;
    private BigDecimal shippingCost;
    private Boolean freeShipping;
    private Boolean inStock;
    private Boolean comingSoon;
    private Boolean customizable;
    private Boolean hasModel;
    private String modelFileUrl;
    private Boolean featured;
    private Boolean bestseller;

    private List<String> images;
    private List<ProductStockDTO> stock;
}