package com.bessisebzemeyve.controller;

import com.bessisebzemeyve.model.ProductResponseDTO;
import com.bessisebzemeyve.model.SaveUpdateProductRequestDTO;
import com.bessisebzemeyve.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@RequestParam(name = "type", defaultValue = "ALL TYPES") String type,
                                                                   @RequestParam(name = "name", defaultValue = "") String productName){
        LOGGER.info("A get all products request has been sent.");
        List<ProductResponseDTO> productResponseDTOS = productService.getProductsByType(productName, type);
        return ResponseEntity.ok(productResponseDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable long id){
        LOGGER.info("A get product request has been sent.");
        ProductResponseDTO productResponseDTOS = productService.getProduct(id);
        return ResponseEntity.ok(productResponseDTOS);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable long id){
        LOGGER.info("A delete product request has been sent");
        ProductResponseDTO productResponseDTO = productService.deleteProduct(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> saveProduct(@Valid @RequestBody SaveUpdateProductRequestDTO dto){
        LOGGER.info("A product register request has been sent with name: {}", dto.getName());
        ProductResponseDTO productResponseDTO = productService.saveProduct(dto);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> updatePokemon(@PathVariable long id, @Valid @RequestBody SaveUpdateProductRequestDTO dto){
        LOGGER.info("A product update request has been sent with name: {}", dto.getName());
        ProductResponseDTO productResponseDTO = productService.updateProduct(dto, id);
        return ResponseEntity.ok(productResponseDTO);
    }
}
