package com.denisdimarco.orderapi.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private Long id;
    private String registerDate;
    private List<OrderLineDTO> lines;
    private Double total;

}
