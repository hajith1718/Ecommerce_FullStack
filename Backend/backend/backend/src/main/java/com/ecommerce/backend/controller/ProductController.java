package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 GET /api/products/search?name=phone
    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // 💲 GET /api/products/price/less?max=1000
    @GetMapping("/price/less")
    public List<Product> priceLessThan(@RequestParam Double max) {
        return productRepository.findByPriceLessThan(max);
    }

    // 💰 GET /api/products/price/greater?min=1000
    @GetMapping("/price/greater")
    public List<Product> priceGreaterThan(@RequestParam Double min) {
        return productRepository.findByPriceGreaterThan(min);
    }

    // 💸 GET /api/products/price/range?min=500&max=1500
    @GetMapping("/price/range")
    public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    // GET /api/products/search/filter?name=phone&min=100&max=2000
    @GetMapping("/search/filter")
    public List<Product> filterProducts(
            @RequestParam String name,
            @RequestParam Double min,
            @RequestParam Double max) {
        return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, min, max);
    }

    // GET /api/products/category?category=electronics
    @GetMapping("/category")
    public List<Product> getByCategory(@RequestParam String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    // GET /api/products/available?minQty=5
    @GetMapping("/available")
    public List<Product> getByMinimumQuantity(@RequestParam int minQty) {
        return productRepository.findByQuantityGreaterThan(minQty);
    }

    // GET /api/products/category/available?category=electronics&minQty=5
    @GetMapping("/category/available")
    public List<Product> getByCategoryAndMinQuantity(
            @RequestParam String category,
            @RequestParam int minQty) {
        return productRepository.findByCategoryIgnoreCaseAndQuantityGreaterThan(category, minQty);
    }

    // GET /api/products/search/paginated?name=phone&page=0&size=5&sort=price,asc
    @GetMapping("/search/paginated")
    public Page<Product> getPaginatedProducts(
            @RequestParam String name,
            Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // GET /api/products/paginated?page=0&size=5&sort=price,desc
    @GetMapping("/paginated")
    public Page<Product> getAllPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
