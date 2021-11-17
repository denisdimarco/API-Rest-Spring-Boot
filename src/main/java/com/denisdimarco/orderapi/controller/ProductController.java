package com.denisdimarco.orderapi.controller;


import com.denisdimarco.orderapi.converter.ProductConverter;
import com.denisdimarco.orderapi.dto.ProductDTO;
import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize
    ) {

        Pageable page = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productService.findAll(page);
        List<ProductDTO> dtoProducts = productConverter.fromEntity(products);
        return new ResponseEntity<List<ProductDTO>>(dtoProducts, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        ProductDTO productDTO = productConverter.fromEntity(product);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product) {

        Product newProduct = productService.save(productConverter.fromDTO(product));
        ProductDTO productDTO = productConverter.fromEntity(newProduct);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO product) {
        Product updatedProduct = productService.save(productConverter.fromDTO(product));
        ProductDTO productDTO = productConverter.fromEntity(updatedProduct);
        return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);

    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return new ResponseEntity(HttpStatus.OK);

    }
}

