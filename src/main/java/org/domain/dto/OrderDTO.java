package org.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal shippingCost;

    // Customer
    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private String customerPhone;

    // Shipping
    private String shippingAddress;
    private String shippingCity;
    private String shippingCap;

    private String notes;
    private String paymentMethod;

    private List<OrderItemDTO> items;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}