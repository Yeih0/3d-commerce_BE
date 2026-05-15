package org.domain.transformer;

import org.domain.dto.CustomizationRequestDTO;
import org.domain.dto.OrderDTO;
import org.domain.dto.OrderItemDTO;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;
import org.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderConverterImpl implements OrderConverter{
    @Override
    public OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingCost(order.getShippingCost());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerSurname(order.getCustomerSurname());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setShippingCity(order.getShippingCity());
        dto.setShippingCap(order.getShippingCap());
        dto.setNotes(order.getNotes());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        dto.setItems(order.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public OrderItemDTO convertItemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSelectedMaterial(item.getSelectedMaterial());
        dto.setSelectedColor(item.getSelectedColor());
        dto.setHasCustomization(item.getHasCustomization());

        if (item.getCustomizationRequest() != null) {
            dto.setCustomizationRequest(convertCustomizationToDTO(item.getCustomizationRequest(), item.getOrder()));
        }

        return dto;
    }

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

    // items
    public OrderItemDTO convertToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSelectedMaterial(item.getSelectedMaterial());
        dto.setSelectedColor(item.getSelectedColor());
        dto.setHasCustomization(item.getHasCustomization());
        return dto;
    }
}