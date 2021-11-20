package com.denisdimarco.orderapi.converter;

import com.denisdimarco.orderapi.dto.SignUpRequestDTO;
import com.denisdimarco.orderapi.dto.UserDTO;
import com.denisdimarco.orderapi.entity.User;

public class UserConverter extends AbstractConverter<User, UserDTO>{


    @Override
    public UserDTO fromEntity(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public User fromDTO(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .build();
    }

    public User signup(SignUpRequestDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
