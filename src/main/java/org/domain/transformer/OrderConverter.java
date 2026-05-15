package org.domain.transformer;

import org.domain.dto.CustomizationRequestDTO;
import org.domain.dto.OrderDTO;
import org.domain.dto.OrderItemDTO;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;
import org.domain.model.OrderItem;

public interface OrderConverter {

    public OrderDTO convertToDTO(Order order);

    public OrderItemDTO convertItemToDTO(OrderItem item);

    public CustomizationRequestDTO convertCustomizationToDTO(CustomizationRequest req, Order order);

    public OrderItemDTO convertToDTO(OrderItem item);
}