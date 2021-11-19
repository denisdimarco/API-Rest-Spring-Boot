package com.denisdimarco.orderapi.service;

import com.denisdimarco.orderapi.entity.Order;
import com.denisdimarco.orderapi.entity.OrderLine;
import com.denisdimarco.orderapi.exception.GeneralServiceException;
import com.denisdimarco.orderapi.exception.NoDataFoundException;
import com.denisdimarco.orderapi.exception.ValidateServiceException;
import com.denisdimarco.orderapi.repository.OrderLineRepository;
import com.denisdimarco.orderapi.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;


    public List<Order> findAll(Pageable page) {
        try {
            return orderRepository.findAll(page).toList();

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Order findById(Long id) {
        try {
            return orderRepository.findById(id).orElseThrow(() -> new NoDataFoundException("Order does not exist."));
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Order does not exist."));
            orderRepository.delete(order);
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Order save(Order order) {
        try {
            order.getLines().forEach(line -> line.setOrder(order));

            if (order.getId() == null) {
                //create orders
                order.setRegisterDate(LocalDateTime.now());
                return orderRepository.save(order);
            }
            //UPDATE

            //validator
            Order savedOrder = orderRepository.findById(order.getId())
                    .orElseThrow(() -> new NoDataFoundException("Order does not exist."));
            order.setRegisterDate(savedOrder.getRegisterDate());

            //To erase deleted lines
            List<OrderLine> deletedLines = savedOrder.getLines();
            deletedLines.removeAll(order.getLines());
            orderLineRepository.deleteAll(deletedLines);

            return orderRepository.save(order);
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
