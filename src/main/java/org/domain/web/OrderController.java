package org.domain.web;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.domain.dto.ApiResponse;
import org.domain.dto.CheckoutRequest;
import org.domain.dto.OrderDTO;
import org.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderDTO>> chekcout(@Valid @RequestBody CheckoutRequest request) throws BadRequestException {
        OrderDTO order = orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order sent successfully", order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrder(@PathVariable Long id){
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Ordine trovato", order));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getUserOrders(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Ordini utente", orders));
    }


}