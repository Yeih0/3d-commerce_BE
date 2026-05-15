package org.domain.service;

import org.domain.dto.CustomizationRequestDTO;
import org.domain.dto.OrderDTO;
import org.domain.exception.ResourceNotFoundException;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;
import org.domain.repository.CustomizationRequestRepository;
import org.domain.repository.OrderRepository;
import org.domain.transformer.CustomizationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomizationService {

    @Autowired
    private CustomizationConverter customizationConverter;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomizationRequestRepository customizationRequestRepository;


    public List<CustomizationRequestDTO> getAllCustomization() {
        List<CustomizationRequest> customDTO = customizationRequestRepository.findAll();
        return customDTO.stream()
                .map(c -> customizationConverter.convertCustomizationToDTO(c, retriveOrder(c)))
                .collect(Collectors.toList());

    }

    public List<CustomizationRequestDTO> getPendingCustomizations() {
        List<CustomizationRequest> customPending = customizationRequestRepository.findPendingRequests();
        return customPending.stream()
                .map(c -> customizationConverter.convertCustomizationToDTO(c, retriveOrder(c)))
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomizationRequestDTO updateStatus(Long id, String status){
        CustomizationRequest customRequest = customizationRequestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Personalizzazione non trovata"));

        customRequest.setStatus(CustomizationRequest.CustomizationStatus.valueOf(status));
        CustomizationRequest savedRequest = customizationRequestRepository.save(customRequest);
        return customizationConverter.convertCustomizationToDTO(savedRequest, retriveOrder(savedRequest));
    }

    private Order retriveOrder(CustomizationRequest customizationRequest) {
        return orderRepository.findById(customizationRequest.getOrderItem().getOrder().getId()).
                orElseThrow(() -> new ResourceNotFoundException("Nessun ordine corrispondente"));
    }
}