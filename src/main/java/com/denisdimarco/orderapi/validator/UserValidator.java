package com.denisdimarco.orderapi.validator;

import com.denisdimarco.orderapi.entity.User;
import com.denisdimarco.orderapi.exception.ValidateServiceException;

public class UserValidator {

    public static void signup(User user) {
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidateServiceException("Username field is required");
        }

        if(user.getUsername().length() > 30) {
            throw new ValidateServiceException("Username value length exceeds 30 characters");
        }

        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            throw  new ValidateServiceException("Password field is required");
        }
        if(user.getPassword().length() > 30)  {
            throw new ValidateServiceException("Password value length exceeds 30 characters");
        }

    }
}
