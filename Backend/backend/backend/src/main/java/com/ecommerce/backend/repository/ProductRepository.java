package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
