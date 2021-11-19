package com.denisdimarco.orderapi.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private long id;
    private String registerDate;
    private List<OrderLineDTO> lines;
    private Double total;

}
