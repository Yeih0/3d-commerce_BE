package org.domain.web;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.domain.dto.*;
import org.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomizationService customizationService;

    @Autowired
    private ContactMessageService contactMessageService;

    @Autowired
    private OrderItemService orderItemService;

    // PRODOTTI
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.createProduct(productDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Prodotto creato con successo", newProduct));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO productToUpdate ,@PathVariable Long id){
        ProductDTO productUpdated = productService.updateProduct(id, productToUpdate);
        return ResponseEntity
                .ok(ApiResponse.success("Prodotto aggiornato con successo", productUpdated));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity
                .ok(ApiResponse.success("Prodotto aggiornato con successo", null));
    }

    // ORDINI
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> deleteProduct(){
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Tutti gli ordini", orders));
    }

    @PatchMapping("orders/{id}/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderDTO orderUpdated = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success("Ordine aggiornato con successo", orderUpdated));
    }

    // PERSONALIZZAZIONI
    @GetMapping("/customizations")
    public ResponseEntity<ApiResponse<List<CustomizationRequestDTO>>> getAllCustomizations() {
        return ResponseEntity.ok(ApiResponse.success("Ordine aggiornato con successo", customizationService.getAllCustomization()));
    }

    @GetMapping("/customizations/pending")
    public ResponseEntity<ApiResponse<List<CustomizationRequestDTO>>> getPendingCustomizations() {
        List<CustomizationRequestDTO> pending = customizationService.getPendingCustomizations();
        return ResponseEntity.ok(ApiResponse.success("Personalizzazioni in sospeso", pending));
    }

    @PatchMapping("/customizations/{id}/status")
    public ResponseEntity<ApiResponse<CustomizationRequestDTO>> updateCustomizationStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomizationStatusRequest request) {
        CustomizationRequestDTO updated = customizationService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success("Stato personalizzazione aggiornato", updated));
    }

    @GetMapping("/contact-messages")
    public ResponseEntity<ApiResponse<List<ContactMessageDTO>>> getAllContactMessages() {
        List<ContactMessageDTO> messages = contactMessageService.getAllMessages();
        return ResponseEntity.ok(ApiResponse.success("Messaggi di contatto", messages));
    }

    @GetMapping("/contact-messages/unread")
    public ResponseEntity<ApiResponse<List<ContactMessageDTO>>> getUnreadMessages() {
        List<ContactMessageDTO> messages = contactMessageService.getUnreadMessages();
        return ResponseEntity.ok(ApiResponse.success("Messaggi non letti", messages));
    }

    @GetMapping("/contact-messages/unread/count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        long count = contactMessageService.countUnreadMessages();
        return ResponseEntity.ok(ApiResponse.success("Conteggio non letti", count));
    }

    @PatchMapping("/contact-messages/{id}/read")
    public ResponseEntity<ApiResponse<ContactMessageDTO>> markMessageAsRead(@PathVariable Long id) {
        ContactMessageDTO message = contactMessageService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Messaggio segnato come letto", message));
    }

    @DeleteMapping("/contact-messages/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContactMessage(@PathVariable Long id) {
        contactMessageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Messaggio eliminato", null));
    }

//GESTIONE IMMAGINI PRODOTTI

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ApiResponse<ProductDTO>> addProductImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) Boolean isPrimary) throws BadRequestException {

        ProductDTO product = productService.addProductImage(productId, file, isPrimary);
        return ResponseEntity.ok(ApiResponse.success("Immagine aggiunta", product));
    }

    @DeleteMapping("/products/{productId}/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) throws BadRequestException {

        productService.deleteProductImage(productId, imageId);
        return ResponseEntity.ok(ApiResponse.success("Immagine eliminata", null));
    }

    @PatchMapping("/products/{productId}/images/{imageId}/primary")
    public ResponseEntity<ApiResponse<ProductDTO>> setPrimaryImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {

        ProductDTO product = productService.setPrimaryImage(productId, imageId);
        return ResponseEntity.ok(ApiResponse.success("Immagine principale impostata", product));
    }

// STATISTICHE ORDER ITEMS

    @GetMapping("/order-items/customizations")
    public ResponseEntity<ApiResponse<List<OrderItemDTO>>> getItemsWithCustomization() {
        List<OrderItemDTO> items = orderItemService.getItemsWithCustomization();
        return ResponseEntity.ok(ApiResponse.success("Items con personalizzazione", items));
    }
}