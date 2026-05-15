package org.domain.repository;

import org.domain.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    List<ProductStock> findByProductId(Long productId);

    Optional<ProductStock> findByProductIdAndMaterialAndColor(Long productId, String material, String color);
}