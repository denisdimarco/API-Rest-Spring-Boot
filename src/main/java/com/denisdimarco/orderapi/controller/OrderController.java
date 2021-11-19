package com.denisdimarco.orderapi.controller;

import com.denisdimarco.orderapi.converter.OrderConverter;
import com.denisdimarco.orderapi.dto.OrderDTO;
import com.denisdimarco.orderapi.entity.Order;
import com.denisdimarco.orderapi.utils.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ){

        Pageable page = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = null; //TODO orderService.findAll(page);

        OrderConverter converter = new OrderConverter();
        return new WrapperResponse(true, "success", converter.fromEntity(orders))
                .createResponse();
    }

}
