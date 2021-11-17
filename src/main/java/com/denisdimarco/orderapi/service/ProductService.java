package com.denisdimarco.orderapi.service;

import com.denisdimarco.orderapi.entity.Product;
import com.denisdimarco.orderapi.exception.GeneralServiceException;
import com.denisdimarco.orderapi.exception.NoDataFoundException;
import com.denisdimarco.orderapi.exception.ValidateServiceException;
import com.denisdimarco.orderapi.repository.ProductRepository;
import com.denisdimarco.orderapi.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long productId) {
        try {
            return productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("The product does not exist"));
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }

    }

    public List<Product> findAll(Pageable page) {
        try {
            return productRepository.findAll(page).toList();
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    public Product save(Product product) {

        try {
            ProductValidator.save(product);

            if (product.getId() == null) {
                return productRepository.save(product);
            }
            Product updatedProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new NoDataFoundException("The product does not exist"));

            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            productRepository.save(updatedProduct);
            return updatedProduct;

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }


    @Transactional
    public void delete(Long productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException("The product does not exist"));
            productRepository.delete(product);
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

}
