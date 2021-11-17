package com.denisdimarco.orderapi.service;

import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.repository.ProductRepository;
import com.denisdimarco.orderapi.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("The product does not exist"));
    }

    public List<Product> findAll(Pageable page) {
        return productRepository.findAll(page).toList();
    }

    @Transactional
    public Product save(Product product) {

        ProductValidator.save(product);

        if (product.getId() == null) {
            return productRepository.save(product);
        }
        Product updatedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("The product does not exist"));

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        productRepository.save(updatedProduct);
        return updatedProduct;
    }


    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("The product does not exist"));
        productRepository.delete(product);
    }

}
