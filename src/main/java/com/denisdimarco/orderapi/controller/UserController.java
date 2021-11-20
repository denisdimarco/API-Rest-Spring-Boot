package com.denisdimarco.orderapi.controller;

import com.denisdimarco.orderapi.converter.UserConverter;
import com.denisdimarco.orderapi.dto.SignUpRequestDTO;
import com.denisdimarco.orderapi.dto.UserDTO;
import com.denisdimarco.orderapi.entity.User;
import com.denisdimarco.orderapi.service.UserService;
import com.denisdimarco.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    public ResponseEntity<WrapperResponse<UserDTO>> signUp(@RequestBody SignUpRequestDTO request) {


        User user = userService.createUser(userConverter.signup(request));
        return new WrapperResponse<>(true, "success", userConverter.fromEntity(user))
                .createResponse();
    }
}
