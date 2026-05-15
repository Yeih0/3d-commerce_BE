package org.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutRequest {

    // Customer Info
    @NotBlank(message = "Nome è obbligatorio")
    private String customerName;

    @NotBlank(message = "Cognome è obbligatorio")
    private String customerSurname;

    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Email non valida")
    private String customerEmail;

    private String customerPhone;

    @NotBlank(message = "Data di nascita è obbligatoria")
    private String customerBirthDate;

    // Shipping Address
    @NotBlank(message = "Indirizzo è obbligatorio")
    private String shippingAddress;

    @NotBlank(message = "Città è obbligatoria")
    private String shippingCity;

    @NotBlank(message = "CAP è obbligatorio")
    private String shippingCap;

    private String notes;

    // Payment
    @NotBlank(message = "Metodo di pagamento è obbligatorio")
    private String paymentMethod;

    // Cart Items
    @NotEmpty(message = "Il carrello è vuoto")
    private List<CheckoutItemRequest> items;

    @NotNull(message = "Totale è obbligatorio")
    private BigDecimal totalAmount;

    @NotNull(message = "Costo spedizione è obbligatorio")
    private BigDecimal shippingCost;
}