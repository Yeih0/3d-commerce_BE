package org.domain.service;

import org.domain.dto.ContactMessageDTO;
import org.domain.exception.ResourceNotFoundException;
import org.domain.model.ContactMessage;
import org.domain.repository.ContactMessageRepository;
import org.domain.transformer.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private MessageConverter messageConverter;

    /**
     * Recupera tutti i messaggi
     */
    public List<ContactMessageDTO> getAllMessages() {
        return contactMessageRepository.findAllOrderByCreatedAtDesc().stream()
                .map((m) -> messageConverter.convertToDTO(m))
                .collect(Collectors.toList());
    }

    /**
     * Recupera messaggi non letti
     */
    public List<ContactMessageDTO> getUnreadMessages() {
        return contactMessageRepository.findByIsReadFalseOrderByCreatedAtDesc().stream()
                .map((m) -> messageConverter.convertToDTO(m))
                .collect(Collectors.toList());
    }

    /**
     * Recupera singolo messaggio
     */
    public ContactMessageDTO getMessageById(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Messaggio non trovato"));
        return messageConverter.convertToDTO(message);
    }

    /**
     * Segna messaggio come letto
     */
    @Transactional
    public ContactMessageDTO markAsRead(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Messaggio non trovato"));

        message.setIsRead(true);
        ContactMessage updated = contactMessageRepository.save(message);
        return messageConverter.convertToDTO(updated);
    }

    /**
     * Elimina messaggio
     */
    @Transactional
    public void deleteMessage(Long id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Messaggio non trovato"));
        contactMessageRepository.delete(message);
    }

    /**
     * Conta messaggi non letti
     */
    public long countUnreadMessages() {
        return contactMessageRepository.findByIsReadFalseOrderByCreatedAtDesc().size();
    }
}