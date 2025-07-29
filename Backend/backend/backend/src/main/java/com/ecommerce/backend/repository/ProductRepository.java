package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search products with name containing given keyword (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Search products with price less than
    List<Product> findByPriceLessThan(Double maxPrice);

    // Search products with price greater than
    List<Product> findByPriceGreaterThan(Double minPrice);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double min, Double max);

    // Filter by Category
    List<Product> findByCategoryIgnoreCase(String category);

    // Filter by Quantity Greater Than
    List<Product> findByQuantityGreaterThan(int quantity);

    // Combine: Category + Quantity
    List<Product> findByCategoryIgnoreCaseAndQuantityGreaterThan(String category, int quantity);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

}
