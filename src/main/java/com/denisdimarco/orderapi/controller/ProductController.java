package com.denisdimarco.orderapi.controller;


import com.denisdimarco.orderapi.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

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
    public List<Product> findAll() {
        return this.products;
    }

    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable("productId") Long productId) {
        for (Product prod : this.products) {
            if (prod.getId().longValue() == productId.longValue()) {
                return prod;
            }
        }
        return null;
    }

    @PostMapping("/products")
    public Product create(@RequestBody Product product) {

        this.products.add(product);
        return product;
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product product) {
        for (Product prod : this.products) {
            if (prod.getId().longValue() == product.getId().longValue()) {
                prod.setName(product.getName());
                return prod;
            }
        }
        throw new RuntimeException("Product ID not exist");
    }

    @DeleteMapping("products/{productId}")
    public void delete(@PathVariable("productId") Long productId) {
        Product deleteProduct = null;

        for (Product prod : this.products) {
            if (prod.getId().longValue() == productId.longValue()) {
                deleteProduct = prod;
                break;
            }
        }
        if (deleteProduct == null) throw new RuntimeException("Product ID not exist");

        this.products.remove(deleteProduct);


    }
}

