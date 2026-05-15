package org.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactMessageRequest {

    @NotBlank(message = "Nome è obbligatorio")
    private String name;

    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Email non valida")
    private String email;

    private String phone;

    @NotBlank(message = "Oggetto è obbligatorio")
    private String subject;

    @NotBlank(message = "Messaggio è obbligatorio")
    private String message;
}