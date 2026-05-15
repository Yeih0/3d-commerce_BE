package org.domain.web;

import org.apache.coyote.BadRequestException;
import org.domain.dto.AuthResponse;
import org.domain.dto.LoginRequest;
import org.domain.dto.RegisterRequest;
import org.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = authService.login(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login effettuato con successo");
            response.put("data", authResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Credenziali non valide");
            errorResponse.put("data", null);

            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    /**
     * Register endpoint
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse authResponse = authService.register(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registrazione completata con successo");
            response.put("data", authResponse);

            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("data", null);

            return ResponseEntity.status(400).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Errore durante la registrazione");
            errorResponse.put("data", null);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Test endpoint per verificare che l'autenticazione funzioni
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Auth controller funzionante");

        return ResponseEntity.ok(response);
    }

}