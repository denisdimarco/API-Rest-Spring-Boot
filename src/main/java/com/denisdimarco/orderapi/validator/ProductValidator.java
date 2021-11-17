package com.denisdimarco.orderapi.validator;

import com.denisdimarco.orderapi.entity.Product;

public class ProductValidator {
    public static void save(Product product) {
        if(product.getName() == null || product.getName().trim().isEmpty()) {
            throw  new RuntimeException("Name field is required");
        }

        if(product.getName().length() > 100) {
            throw new RuntimeException("Name length is too long: (max 100)");
        }

        if(product.getPrice() == null) {
            throw new RuntimeException("Price field is required");
        }
        if(product.getPrice() < 0) {
            throw  new RuntimeException("Price ");
        }
    }
}
