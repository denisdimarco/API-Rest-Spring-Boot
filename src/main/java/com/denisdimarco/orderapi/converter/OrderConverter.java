package com.denisdimarco.orderapi.converter;

import com.denisdimarco.orderapi.dto.OrderDTO;
import com.denisdimarco.orderapi.dto.OrderLineDTO;
import com.denisdimarco.orderapi.entity.Order;
import com.denisdimarco.orderapi.entity.OrderLine;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderConverter extends AbstractConverter<Order, OrderDTO>{

    private DateTimeFormatter dateTimeFormat;
    private ProductConverter productConverter;

    @Override
    public OrderDTO fromEntity(Order entity) {

        if (entity == null) return null;

        List<OrderLineDTO> lines = fromOrderLineEntity(entity.getLines());

        return OrderDTO.builder()
                .id(entity.getId())
                .lines(lines)
                .registerDate(entity.getRegisterDate().format(dateTimeFormat))
                .total(entity.getTotal())
                .build();
    }

    @Override
    public Order fromDTO(OrderDTO dto) {
        if (dto == null) return null;

        List<OrderLine> lines = fromOrderLineDTO(dto.getLines());

        return Order.builder()
                .id(dto.getId())
                .lines(lines)
                .total(dto.getTotal())
                .build();
    }

    private List<OrderLineDTO> fromOrderLineEntity(List<OrderLine> lines) {
        if (lines == null) return null;

        return lines.stream().map(line -> {

            return OrderLineDTO.builder()
                    .id(line.getId())
                    .price(line.getPrice())
                    .product(productConverter.fromEntity(line.getProduct()))
                    .quantity(line.getQuantity())
                    .total(line.getTotal())
                    .build();
        })
                .collect(Collectors.toList());
    }
    private List<OrderLine> fromOrderLineDTO(List<OrderLineDTO> lines) {

        if (lines == null) return null;

        return lines.stream().map(line -> {

                    return OrderLine.builder()
                            .id(line.getId())
                            .price(line.getPrice())
                            .product(productConverter.fromDTO(line.getProduct()))
                            .quantity(line.getQuantity())
                            .total(line.getTotal())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
