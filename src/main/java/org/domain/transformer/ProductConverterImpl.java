package org.domain.transformer;

import org.domain.dto.ProductDTO;
import org.domain.dto.ProductStockDTO;
import org.domain.model.Product;
import org.domain.model.ProductStock;
import org.domain.model.ProductImage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductConverterImpl implements ProductConverter{
    @Override
    public ProductStockDTO convertStockToDTO(ProductStock stock) {
        ProductStockDTO dto = new ProductStockDTO();
        dto.setId(stock.getId());
        dto.setMaterial(stock.getMaterial());
        dto.setColor(stock.getColor());
        dto.setQuantity(stock.getQuantity());
        return dto;
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory().name());
        dto.setDimensions(product.getDimensions());
        dto.setProcessingDays(product.getProcessingDays());
        dto.setShippingDays(product.getShippingDays());
        dto.setShippingCost(product.getShippingCost());
        dto.setFreeShipping(product.getFreeShipping());
        dto.setInStock(product.getInStock());
        dto.setComingSoon(product.getComingSoon());
        dto.setCustomizable(product.getCustomizable());
        dto.setHasModel(product.getHasModel());
        dto.setModelFileUrl(product.getModelFileUrl());
        dto.setFeatured(product.getFeatured());
        dto.setBestseller(product.getBestseller());

        // Images
        dto.setImages(product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList()));

        // Stock
        dto.setStock(product.getStock().stream()
                .map(this::convertStockToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(Product.ProductCategory.valueOf(dto.getCategory()));
        product.setDimensions(dto.getDimensions());
        product.setProcessingDays(dto.getProcessingDays());
        product.setShippingDays(dto.getShippingDays());
        product.setShippingCost(dto.getShippingCost());
        product.setFreeShipping(dto.getFreeShipping());
        product.setInStock(dto.getInStock());
        product.setComingSoon(dto.getComingSoon());
        product.setCustomizable(dto.getCustomizable());
        product.setHasModel(dto.getHasModel());
        product.setModelFileUrl(dto.getModelFileUrl());
        product.setFeatured(dto.getFeatured());
        product.setBestseller(dto.getBestseller());
        return product;
    }
}