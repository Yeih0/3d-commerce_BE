package org.domain.repository;

import org.domain.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findByIsReadFalseOrderByCreatedAtDesc();

    @Query("SELECT cm FROM ContactMessage cm ORDER BY cm.createdAt DESC")
    List<ContactMessage> findAllOrderByCreatedAtDesc();
}