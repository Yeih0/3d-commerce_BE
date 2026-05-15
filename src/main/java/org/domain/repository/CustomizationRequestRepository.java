package org.domain.repository;

import org.domain.model.CustomizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizationRequestRepository extends JpaRepository<CustomizationRequest, Long> {

    List<CustomizationRequest> findByStatusOrderByCreatedAtDesc(CustomizationRequest.CustomizationStatus status);

    @Query("SELECT cr FROM CustomizationRequest cr ORDER BY cr.createdAt DESC")
    List<CustomizationRequest> findAllOrderByCreatedAtDesc();

    @Query("SELECT cr FROM CustomizationRequest cr WHERE cr.status = 'PENDING' ORDER BY cr.createdAt DESC")
    List<CustomizationRequest> findPendingRequests();
}