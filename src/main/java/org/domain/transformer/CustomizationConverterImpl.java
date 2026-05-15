package org.domain.transformer;

import org.domain.dto.CustomizationRequestDTO;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class CustomizationConverterImpl implements CustomizationConverter {
    @Override
    public CustomizationRequestDTO convertCustomizationToDTO(CustomizationRequest req, Order order) {
        CustomizationRequestDTO dto = new CustomizationRequestDTO();
        dto.setId(req.getId());
        dto.setDescription(req.getDescription());
        dto.setEstimatedDays(req.getEstimatedDays());
        dto.setFont(req.getFont());
        dto.setCustomerPhone(req.getCustomerPhone());
        dto.setStatus(req.getStatus().name());
        dto.setCreatedAt(req.getCreatedAt());
        dto.setOrderId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setProductName(req.getOrderItem().getProduct().getName());
        return dto;
    }
}