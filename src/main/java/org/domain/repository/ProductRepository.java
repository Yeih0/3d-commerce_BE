package org.domain.repository;

import org.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Product.ProductCategory category);

    List<Product> findByFeaturedTrue();

    List<Product> findByBestsellerTrue();

    List<Product> findByInStockTrue();

    List<Product> findByComingSoonTrue();

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inStock = true")
    List<Product> findByCategoryAndInStock(@Param("category") Product.ProductCategory category);
}