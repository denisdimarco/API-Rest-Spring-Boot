package com.denisdimarco.orderapi.converter;

import com.denisdimarco.orderapi.dto.ProductDTO;
import com.denisdimarco.orderapi.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter extends AbstractConverter<Product , ProductDTO> {

    @Override
    public ProductDTO fromEntity(Product entity) {

        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Product fromDTO(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }
}
