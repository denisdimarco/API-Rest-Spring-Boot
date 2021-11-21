package com.denisdimarco.orderapi.config;

import com.denisdimarco.orderapi.converter.OrderConverter;
import com.denisdimarco.orderapi.converter.ProductConverter;
import com.denisdimarco.orderapi.converter.UserConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class ConverterConfig {
    @Value("${config.datetimeFormat}")
    private String datetimeFormat;

    @Bean
    public ProductConverter getProductConverter() {
        return  new ProductConverter();
    }

    @Bean
    public OrderConverter getOrderConverter() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(datetimeFormat);
        return new OrderConverter(format, getProductConverter(), getUserConverter());
    }

    @Bean
    public UserConverter getUserConverter() {
        return new UserConverter();
    }
}
