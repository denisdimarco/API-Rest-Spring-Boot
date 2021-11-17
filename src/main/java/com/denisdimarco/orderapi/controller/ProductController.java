package com.denisdimarco.orderapi.controller;


import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private List<Product> products = new ArrayList<>();

    public ProductController() {

        for (int c = 0; c < 10; c++) {
            products.add(Product.builder()
                    .id(c + 1L)
                    .name("Product " + (c + 1L))
                    .build());
        }


    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("The product does not exist"));
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product newProduct = productRepository.save(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        Product updatedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("The product does not exist"));

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        productRepository.save(updatedProduct);

        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);

    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("The product does not exist"));

        productRepository.delete(product);
        return new ResponseEntity(HttpStatus.OK);


    }
}

