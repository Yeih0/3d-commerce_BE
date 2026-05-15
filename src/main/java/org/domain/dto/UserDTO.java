package org.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private String role;
    private Boolean isActive;
    private LocalDateTime createdAt;
}