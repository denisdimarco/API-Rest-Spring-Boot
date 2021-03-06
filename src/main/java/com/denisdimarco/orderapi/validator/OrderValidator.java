package com.denisdimarco.orderapi.validator;

import com.denisdimarco.orderapi.entity.Order;
import com.denisdimarco.orderapi.entity.OrderLine;
import com.denisdimarco.orderapi.exception.ValidateServiceException;

public class OrderValidator {

    public static void save(Order order) {

        if (order.getLines() == null || order.getLines().isEmpty()) {
            throw new ValidateServiceException("Lines field is required");
        }

        for (OrderLine line : order.getLines()) {

            if (line.getProduct() == null || line.getProduct().getId() == null) {
                throw new ValidateServiceException("Product field is required");
            }

            if (line.getQuantity() == null) {
                throw new ValidateServiceException("Quantity field is required");
            }

            if (line.getQuantity() < 0) {
                throw new ValidateServiceException("Quantity field not valid");
            }

        }
    }
}
