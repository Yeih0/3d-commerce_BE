package org.domain.service;

import org.domain.dto.OrderItemDTO;
import org.domain.exception.ResourceNotFoundException;
import org.domain.model.OrderItem;
import org.domain.repository.OrderItemRepository;
import org.domain.transformer.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderConverter orderConverter;

    /**
     * Recupera items per ordine
     */
    public List<OrderItemDTO> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map((o) -> orderConverter.convertToDTO(o))
                .collect(Collectors.toList());
    }

    /**
     * Recupera tutti gli items con personalizzazione
     */
    public List<OrderItemDTO> getItemsWithCustomization() {
        return orderItemRepository.findByHasCustomizationTrue().stream()
                .map((o) -> orderConverter.convertToDTO(o))
                .collect(Collectors.toList());
    }

    /**
     * Recupera singolo item
     */
    public OrderItemDTO getItemById(Long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item non trovato"));
        return orderConverter.convertToDTO(item);
    }
}