package com.denisdimarco.orderapi.dto;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderLineDTO {

    private Long id;
    private ProductDTO product;
    private Double price;
    private Double quantity;
    private Double total;
}
