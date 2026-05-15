package org.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Password attuale è obbligatoria")
    private String oldPassword;

    @NotBlank(message = "Nuova password è obbligatoria")
    @Size(min = 6, message = "La password deve essere almeno 6 caratteri")
    private String newPassword;
}