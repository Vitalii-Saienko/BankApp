package com.example.bankapp.mapper;

import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR)
@Component
public interface ProductMapper {
    ProductDto productToProductDto(Product product);
    Set<ProductDto> productsToProductDto(Set<Product> products);

    default String map(Manager value) {
        return String.valueOf(value.getManagerId());
    }
}
