package org.domain.transformer;

import org.domain.dto.ContactMessageDTO;
import org.domain.model.ContactMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterImpl implements MessageConverter {

    public ContactMessageDTO convertToDTO(ContactMessage message) {
        ContactMessageDTO dto = new ContactMessageDTO();
        dto.setId(message.getId());
        dto.setName(message.getName());
        dto.setEmail(message.getEmail());
        dto.setPhone(message.getPhone());
        dto.setSubject(message.getSubject());
        dto.setMessage(message.getMessage());
        dto.setIsRead(message.getIsRead());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
}