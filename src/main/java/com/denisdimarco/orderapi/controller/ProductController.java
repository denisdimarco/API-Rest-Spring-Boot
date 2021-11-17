package com.denisdimarco.orderapi.controller;


import com.denisdimarco.orderapi.dto.ProductDTO;
import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> products = productService.findAll();

       List<ProductDTO> dtoProducts = products.stream().map(product -> {
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        })
                .collect(Collectors.toList());

        return new ResponseEntity<List<ProductDTO>>(dtoProducts, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();

        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@RequestBody Product product) {
        Product newProduct = productService.save(product);

        ProductDTO productDTO = ProductDTO.builder()
                .id(newProduct.getId())
                .name(newProduct.getName())
                .price(newProduct.getPrice())
                .build();

        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> update(@RequestBody Product product) {

        Product updatedProduct = productService.save(product);
        
        ProductDTO productDTO = ProductDTO.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .build();

        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);

    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return new ResponseEntity(HttpStatus.OK);

    }
}

