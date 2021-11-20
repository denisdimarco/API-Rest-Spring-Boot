package com.denisdimarco.orderapi.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    private String username;
    private String password;

}
