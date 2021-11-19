package com.denisdimarco.orderapi.controller;

import com.denisdimarco.orderapi.converter.OrderConverter;
import com.denisdimarco.orderapi.dto.OrderDTO;
import com.denisdimarco.orderapi.entity.Order;
import com.denisdimarco.orderapi.utils.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    OrderConverter converter = new OrderConverter();

    @GetMapping("/orders")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ){

        Pageable page = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = null; //TODO orderService.findAll(page);
        return new WrapperResponse(true, "success", converter.fromEntity(orders))
                .createResponse();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable(name="id") Long id) {
        Order order = null; // orderService.findById(id);
        return new WrapperResponse(true, "success", converter.fromEntity(order))
                .createResponse();
    }

    @PostMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> create(@RequestBody OrderDTO order) {

        Order newOrder = null; // orderService.save(converter.fromDTO(order));

        return new WrapperResponse(true, "success", converter.fromEntity(order))
                .createResponse();
    }

    @PutMapping("/orders")
    public ResponseEntity<WrapperResponse<OrderDTO>> update(@RequestBody OrderDTO order) {

        Order newOrder = null; // orderService.save(converter.fromDTO(order));

        return new WrapperResponse(true, "success", converter.fromEntity(order))
                .createResponse();
    }

    @DeleteMapping("/orders/id")
    public ResponseEntity<?> delete(@PathVariable(name="id") Long id) {
       // orderService.delete(id);

        return new WrapperResponse(true, "success", null).createResponse();
    }
}
