package org.domain.service;

import lombok.extern.log4j.Log4j2;
import org.domain.model.CustomizationRequest;
import org.domain.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {

    @Value("${spring.mail.username:admin@3dprintstore.it}")
    private String adminEmail;

    // email di conferma ordine
    public void sendOrderConfirmation(Order order){
        log.info("invio mail di conferma ordine #{} a {}", order.getId(), order.getCustomerEmail());

        // TODO: Implementare invio email con JavaMailSender o servizio esterno
        String subject = "Conferma Ordine #" + order.getId() + " - 3D Print Store";
        String body = buildOrderConfirmationEmail(order);

        // Simula invio email (in produzione usare Spring Mail o SendGrid/Mailgun)
        log.info("Email Subject: {}", subject);
        log.info("Email Body: {}", body);

        // Configurazione per Spring Mail :
        /*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(order.getCustomerEmail());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        */
    }

    // email richiesta personalizzazione prodotto
    public void sendCustomizationNotification(Order order, CustomizationRequest customization) {
        log.info("invio email personalizzazione per l'ordine #{} all'admin", order.getId());

        String subject = "Nuova Richiesta Personalizzazione - Ordine #" + order.getId();
        String body = buildCustomizationNotificationEmail(order, customization);

        log.info("Email to Admin: {}", adminEmail);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);

        // TODO: Implementare invio reale
    }

    // email aggiornamento ordine al cliente
    public void sendOrderStatusUpdate(Order order) {
        log.info("Invio email aggiornamento stato ordine #{} a: {}", order.getId(), order.getCustomerEmail());

        String subject = "Aggiornamento Ordine #" + order.getId() + " - " + getStatusLabel(order.getStatus().name());
        String body = buildOrderStatusUpdateEmail(order);

        log.info("Subject: {}", subject);
        log.info("Body: {}", body);

        // TODO: Implementare invio reale
    }

    // email dal foprm di contatti
    public void sendContactMessage(String name, String email, String phone, String subject, String message) {
        log.info("Invio email contatto da: {} <{}>", name, email);

        String emailSubject = "Contatto dal sito - " + subject;
        String emailBody = buildContactMessageEmail(name, email, phone, message);

        log.info("Email to Admin: {}", adminEmail);
        log.info("Subject: {}", emailSubject);
        log.info("Body: {}", emailBody);

        // TODO: Implementare invio reale
    }

    private String buildOrderConfirmationEmail(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gentile ").append(order.getCustomerName()).append(" ").append(order.getCustomerSurname()).append(",\n\n");
        sb.append("Grazie per il tuo ordine!\n\n");
        sb.append("DETTAGLI ORDINE\n");
        sb.append("================\n");
        sb.append("Numero Ordine: #").append(order.getId()).append("\n");
        sb.append("Data: ").append(order.getCreatedAt()).append("\n");
        sb.append("Totale: €").append(order.getTotalAmount()).append("\n");
        sb.append("Spedizione: €").append(order.getShippingCost()).append("\n");
        sb.append("\nIndirizzo di Spedizione:\n");
        sb.append(order.getShippingAddress()).append("\n");
        sb.append(order.getShippingCity()).append(", ").append(order.getShippingCap()).append("\n\n");

        sb.append("PRODOTTI:\n");
        order.getItems().forEach(item -> {
            sb.append("- ").append(item.getProduct().getName());
            sb.append(" (x").append(item.getQuantity()).append(")");
            if (item.getSelectedMaterial() != null) {
                sb.append(" - Materiale: ").append(item.getSelectedMaterial());
            }
            if (item.getSelectedColor() != null) {
                sb.append(", Colore: ").append(item.getSelectedColor());
            }
            sb.append("\n");
        });

        sb.append("\nTi contatteremo presto con gli aggiornamenti sulla spedizione.\n\n");
        sb.append("Cordiali saluti,\n");
        sb.append("Il Team di 3D Print Store");

        return sb.toString();
    }

    private String buildCustomizationNotificationEmail(Order order, CustomizationRequest customization) {
        StringBuilder sb = new StringBuilder();
        sb.append("NUOVA RICHIESTA DI PERSONALIZZAZIONE\n\n");
        sb.append("Ordine: #").append(order.getId()).append("\n");
        sb.append("Cliente: ").append(order.getCustomerName()).append(" ").append(order.getCustomerSurname()).append("\n");
        sb.append("Email: ").append(order.getCustomerEmail()).append("\n");
        sb.append("Telefono: ").append(customization.getCustomerPhone()).append("\n\n");

        sb.append("Prodotto: ").append(customization.getOrderItem().getProduct().getName()).append("\n");
        sb.append("Quantità: ").append(customization.getOrderItem().getQuantity()).append("\n\n");

        sb.append("DETTAGLI PERSONALIZZAZIONE:\n");
        sb.append("===========================\n");
        sb.append(customization.getDescription()).append("\n\n");

        if (customization.getFont() != null) {
            sb.append("Font richiesto: ").append(customization.getFont()).append("\n");
        }
        if (customization.getEstimatedDays() != null) {
            sb.append("Tempo stimato: ").append(customization.getEstimatedDays()).append(" giorni\n");
        }

        sb.append("\nContatta il cliente al numero: ").append(customization.getCustomerPhone());

        return sb.toString();
    }

    private String buildOrderStatusUpdateEmail(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gentile ").append(order.getCustomerName()).append(",\n\n");
        sb.append("Il tuo ordine #").append(order.getId()).append(" è stato aggiornato.\n\n");
        sb.append("Nuovo Stato: ").append(getStatusLabel(order.getStatus().name())).append("\n\n");

        switch (order.getStatus()) {
            case PROCESSING:
                sb.append("Stiamo lavorando al tuo ordine!\n");
                break;
            case SHIPPED:
                sb.append("Il tuo ordine è stato spedito! Riceverai presto il tracking.\n");
                break;
            case DELIVERED:
                sb.append("Il tuo ordine è stato consegnato. Grazie per aver scelto 3D Print Store!\n");
                break;
            case CANCELLED:
                sb.append("Il tuo ordine è stato annullato. Per maggiori informazioni contattaci.\n");
                break;
        }

        sb.append("\nCordiali saluti,\n");
        sb.append("Il Team di 3D Print Store");

        return sb.toString();
    }

    private String buildContactMessageEmail(String name, String email, String phone, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("NUOVO MESSAGGIO DAL FORM CONTATTI\n\n");
        sb.append("Da: ").append(name).append("\n");
        sb.append("Email: ").append(email).append("\n");
        if (phone != null && !phone.isEmpty()) {
            sb.append("Telefono: ").append(phone).append("\n");
        }
        sb.append("\nMessaggio:\n");
        sb.append("==========\n");
        sb.append(message);

        return sb.toString();
    }

    private String getStatusLabel(String status) {
        switch (status) {
            case "PENDING": return "In Sospeso";
            case "PROCESSING": return "In Lavorazione";
            case "SHIPPED": return "Spedito";
            case "DELIVERED": return "Consegnato";
            case "CANCELLED": return "Annullato";
            default: return status;
        }
    }
}