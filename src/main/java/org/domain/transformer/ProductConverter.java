package org.domain.transformer;

import org.domain.dto.ProductDTO;
import org.domain.dto.ProductStockDTO;
import org.domain.model.Product;
import org.domain.model.ProductStock;

public interface ProductConverter {

    public ProductStockDTO convertStockToDTO(ProductStock stock);

    public ProductDTO convertToDTO(Product product);

    public Product convertToEntity(ProductDTO dto);
}