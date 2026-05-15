package org.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customization_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomizationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "estimated_days")
    private Integer estimatedDays;

    private String font;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomizationStatus status = CustomizationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum CustomizationStatus {
        PENDING,
        CONFIRMED,
        APPROVED,
        REJECTED,
        COMPLETED
    }
}