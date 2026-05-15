package org.domain.transformer;

import org.domain.dto.CustomizationRequestDTO;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;

public interface CustomizationConverter {

    public CustomizationRequestDTO convertCustomizationToDTO(CustomizationRequest req, Order order);
}