package org.domain.service;

import org.apache.coyote.BadRequestException;
import org.domain.dto.ProductDTO;
import org.domain.exception.ResourceNotFoundException;
import org.domain.model.Product;
import org.domain.model.ProductImage;
import org.domain.model.ProductStock;
import org.domain.repository.ProductImageRepository;
import org.domain.repository.ProductRepository;
import org.domain.repository.ProductStockRepository;
import org.domain.transformer.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> productConverter.convertToDTO(p))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato con id: " + id));
        return productConverter.convertToDTO(product);
    }

    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.searchByName(keyword).stream()
                .map(p -> productConverter.convertToDTO(p))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        Product.ProductCategory productCategory = Product.ProductCategory.valueOf(category);
        return productRepository.findByCategory(productCategory).stream()
                .map(p -> productConverter.convertToDTO(p))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productSaved = productRepository.save(productConverter.convertToEntity(productDTO));
        return productConverter.convertToDTO(productSaved);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato con id: " + id));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDimensions(productDTO.getDimensions());
        product.setProcessingDays(productDTO.getProcessingDays());
        product.setShippingDays(productDTO.getShippingDays());
        product.setShippingCost(productDTO.getShippingCost());
        product.setFreeShipping(productDTO.getFreeShipping());
        product.setInStock(productDTO.getInStock());
        product.setComingSoon(productDTO.getComingSoon());
        product.setCustomizable(productDTO.getCustomizable());
        product.setHasModel(productDTO.getHasModel());
        product.setModelFileUrl(productDTO.getModelFileUrl());
        product.setFeatured(productDTO.getFeatured());
        product.setBestseller(productDTO.getBestseller());

        Product updatedProduct = productRepository.save(product);
        return productConverter.convertToDTO(updatedProduct);
    }

    @Transactional
    public boolean updateStock(Long productId, String material, String color, Integer quantity) {
        ProductStock stock = productStockRepository
                .findByProductIdAndMaterialAndColor(productId, material, color)
                .orElseThrow(() -> new ResourceNotFoundException("Stock non trovato"));

        if (stock.getQuantity() < quantity) {
            return false; // Stock insufficiente
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        productStockRepository.save(stock);
        return true;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nessun prodotto presente con id : " + id));
        productRepository.delete(productToDelete);
    }

    @Transactional
    public ProductDTO addProductImage(Long productId, MultipartFile file, Boolean isPrimary) throws BadRequestException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

        // Upload file
        String imageUrl = fileStorageService.storeFile(file, "products");

        // Se questa deve essere primary, rimuovi flag dalle altre
        if (isPrimary != null && isPrimary) {
            product.getImages().forEach(img -> img.setIsPrimary(false));
        }

        // Crea nuova immagine
        ProductImage image = new ProductImage();
        image.setProduct(product);
        image.setImageUrl(imageUrl);
        image.setIsPrimary(isPrimary != null ? isPrimary : false);
        image.setDisplayOrder(product.getImages().size());

        product.getImages().add(image);
        productRepository.save(product);

        return productConverter.convertToDTO(product);
    }

    @Transactional
    public void deleteProductImage(Long productId, Long imageId) throws BadRequestException {
        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Immagine non trovata"));

        if (!image.getProduct().getId().equals(productId)) {
            throw new BadRequestException("Immagine non appartiene a questo prodotto");
        }

        // Elimina file dal filesystem
        fileStorageService.deleteFile(image.getImageUrl());

        // Elimina record dal DB
        productImageRepository.delete(image);
    }

    @Transactional
    public ProductDTO setPrimaryImage(Long productId, Long imageId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

        // Rimuovi flag primary da tutte
        product.getImages().forEach(img -> img.setIsPrimary(false));

        // Imposta come primary
        ProductImage targetImage = product.getImages().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Immagine non trovata"));

        targetImage.setIsPrimary(true);
        productRepository.save(product);

        return productConverter.convertToDTO(product);
    }
}