package org.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_stock",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "material", "color"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer quantity = 0;
}