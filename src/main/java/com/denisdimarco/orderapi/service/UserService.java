package com.denisdimarco.orderapi.service;


import com.denisdimarco.orderapi.entity.User;
import com.denisdimarco.orderapi.exception.GeneralServiceException;
import com.denisdimarco.orderapi.exception.NoDataFoundException;
import com.denisdimarco.orderapi.exception.ValidateServiceException;
import com.denisdimarco.orderapi.repository.UserRepository;
import com.denisdimarco.orderapi.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        try {
            UserValidator.signup(user);

            User existentUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if (existentUser != null) throw new ValidateServiceException("Username already exist");

            return userRepository.save(user);

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
