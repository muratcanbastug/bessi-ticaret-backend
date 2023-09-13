package com.bessisebzemeyve.repository;

import com.bessisebzemeyve.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    void deleteById(long id);
    List<Product> findAllByTypeAndNameContainsOrderByName(String type, String name);
    List<Product> findByNameContainsOrderByName(String name);
}
