package com.denisdimarco.orderapi.validator;

import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.exception.ValidateServiceException;

public class ProductValidator {
    public static void save(Product product) {
        if(product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidateServiceException("Name field is required");
        }

        if(product.getName().length() > 100) {
            throw new ValidateServiceException("Name length is too long: (max 100)");
        }

        if(product.getPrice() == null) {
            throw new ValidateServiceException("Price field is required");
        }
        if(product.getPrice() < 0) {
            throw  new ValidateServiceException("Price ");
        }
    }
}
