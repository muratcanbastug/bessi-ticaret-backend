package com.bessisebzemeyve.service;

import com.bessisebzemeyve.entity.Product;
import com.bessisebzemeyve.entity.Unit;
import com.bessisebzemeyve.model.ProductResponseDTO;
import com.bessisebzemeyve.model.SaveUpdateProductRequestDTO;
import com.bessisebzemeyve.repository.ProductRepository;
import com.bessisebzemeyve.repository.UnitRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;

    public ProductService(ProductRepository productRepository, UnitRepository unitRepository) {
        this.productRepository = productRepository;
        this.unitRepository = unitRepository;
    }

    public List<ProductResponseDTO> getProductsByType(String productName, String type) {
        if("ALL TYPES".equals(type)) {
            return productRepository.findByNameContainsOrderByName(productName)
                    .stream().map(this::generateResponse).toList();
        }
        return productRepository.findAllByTypeAndNameContainsOrderByName(type, productName)
                .stream().map(this::generateResponse).toList();
    }

    public ProductResponseDTO getProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No product with given id."));
        return generateResponse(product);
    }

    public ProductResponseDTO deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No product with given id."));
        productRepository.deleteById(id);
        return generateResponse(product);
    }

    private ProductResponseDTO generateResponse(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setName(product.getName());
        productResponseDTO.setType(product.getType());
        productResponseDTO.setUnits(product.getUnits().stream().map(Unit::getName).collect(Collectors.toSet()));
        return productResponseDTO;
    }

    public ProductResponseDTO saveProduct(SaveUpdateProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setType(dto.getType());
        Set<Unit> units = new java.util.HashSet<>(Set.of());
        for (int i = 0; i <(dto.getUnits().size()); i++) {
            Unit unit = unitRepository.findByName(dto.getUnits().get(i)).orElseThrow(() -> new BadCredentialsException("Given product type does not exist."));
            units.add(unit);
        }
        product.setUnits(units);
        Product savedProduct = productRepository.save(product);
        return generateResponse(savedProduct);
    }

    public ProductResponseDTO updateProduct(SaveUpdateProductRequestDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No product with given id."));
        product.setName(dto.getName());
        product.setType(dto.getType());
        Set<Unit> units = new java.util.HashSet<>(Set.of());
        for (int i = 0; i <(dto.getUnits().size()); i++) {
            Unit unit = unitRepository.findByName(dto.getUnits().get(i)).orElseThrow(() -> new BadCredentialsException("Given product type does not exist."));
            units.add(unit);
        }
        product.setUnits(units);
        Product savedProduct = productRepository.save(product);
        return generateResponse(savedProduct);
    }

}
