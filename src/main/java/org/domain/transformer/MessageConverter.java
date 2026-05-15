package org.domain.transformer;

import org.domain.dto.ContactMessageDTO;
import org.domain.model.ContactMessage;

public interface MessageConverter {
    public ContactMessageDTO convertToDTO(ContactMessage message);
}