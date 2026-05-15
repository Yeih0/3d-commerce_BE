package org.domain.service;

import org.apache.coyote.BadRequestException;
import org.domain.dto.CheckoutItemRequest;
import org.domain.dto.CheckoutRequest;
import org.domain.dto.OrderDTO;
import org.domain.exception.ResourceNotFoundException;
import org.domain.model.*;
import org.domain.repository.OrderRepository;
import org.domain.repository.ProductRepository;
import org.domain.repository.ProductStockRepository;
import org.domain.transformer.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Transactional
    public OrderDTO createOrder(CheckoutRequest request) throws BadRequestException {

        validateAge(request.getCustomerBirthDate());

        Order order = buildOrder(request);
        Order savedOrder = orderRepository.save(order);

        for(CheckoutItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

            if(itemRequest.getSelectedColor()!=null && itemRequest.getSelectedMaterial()!=null) {
                ProductStock pStock = productStockRepository.
                        findByProductIdAndMaterialAndColor(product.getId(), itemRequest.getSelectedMaterial(), itemRequest.getSelectedColor())
                        .orElseThrow(() -> new ResourceNotFoundException("Stock non trovato per questa combinazione"));

                if(pStock.getQuantity() < itemRequest.getQuantity()) {
                    throw new BadRequestException("Stock insufficiente per " + product.getName());
                }

                pStock.setQuantity(pStock.getQuantity() - itemRequest.getQuantity());
                productStockRepository.save(pStock);
            }

            OrderItem orderItem = buildOrderItem(savedOrder,product, itemRequest);
            savedOrder.getItems().add(orderItem);

            if(itemRequest.getHasCustomization() && itemRequest.getCustomizationDetails()!=null) {
                CustomizationRequest customization = buildCustomization(itemRequest, orderItem);

                orderItem.setCustomizationRequest(customization);
                // Invia email admin per personalizzazione
                //emailService.sendCustomizationNotification(savedOrder, customization);
            }
        }
        Order finalOrder = orderRepository.save(savedOrder);
        // Invia email conferma ordine
        //emailService.sendOrderConfirmation(finalOrder);

        return orderConverter.convertToDTO(finalOrder);
    }

    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(order -> orderConverter.convertToDTO(order))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordine non trovato con id : " + id));
        return orderConverter.convertToDTO(order);
    }

    public List<OrderDTO> getOrderByUserId(Long userId) {
        return orderRepository.findUserOrdersDesc(userId).stream()
                .map(order -> orderConverter.convertToDTO(order))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordine non trovato con id : " + id));
        order.setStatus(Order.OrderStatus.valueOf(status));
        Order updatedOrder = orderRepository.save(order);

        //emailService.sendOrderStatusUpdate(updatedOrder);
        return orderConverter.convertToDTO(updatedOrder);
    }

    private static Order buildOrder(CheckoutRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerSurname(request.getCustomerSurname());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCustomerBirthDate(request.getCustomerBirthDate());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingCity(request.getShippingCity());
        order.setShippingCap(request.getShippingCap());
        order.setNotes(request.getNotes());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setTotalAmount(request.getTotalAmount());
        order.setShippingCost(request.getShippingCost());
        order.setStatus(Order.OrderStatus.PENDING);
        return order;
    }

    private static OrderItem buildOrderItem(Order savedOrder, Product product, CheckoutItemRequest itemRequest){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder);
        orderItem.setProduct(product);
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setSelectedMaterial(itemRequest.getSelectedMaterial());
        orderItem.setSelectedColor(itemRequest.getSelectedColor());
        orderItem.setHasCustomization(itemRequest.getHasCustomization());
        return orderItem;
    }

    private static CustomizationRequest buildCustomization(CheckoutItemRequest itemRequest, OrderItem orderItem) {
        CustomizationRequest customization = new CustomizationRequest();
        customization.setOrderItem(orderItem);
        customization.setDescription(itemRequest.getCustomizationDetails().getDescription());
        customization.setEstimatedDays(itemRequest.getCustomizationDetails().getEstimatedDays());
        customization.setFont(itemRequest.getCustomizationDetails().getFont());
        customization.setCustomerPhone(itemRequest.getCustomizationDetails().getCustomerPhone());
        customization.setStatus(CustomizationRequest.CustomizationStatus.PENDING);
        return customization;
    }

    private void validateAge(String dob) throws BadRequestException {

        try{
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(dob);
            LocalDate birthDate = LocalDate.parse(dob, formatDate);
            int age = Period.between(birthDate, LocalDate.now()).getYears();

            if(age < 18) {
                throw new BadRequestException("L'età minima per effettuare l'ordine è 18 anni");
            }
        } catch (Exception e) {
            throw new BadRequestException("Data di nascita non valida");
        }
    }
}