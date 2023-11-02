package com.example.bankapp.service.util;

import com.example.bankapp.dto.NewManagerForProductRequestDto;
import com.example.bankapp.dto.ProductCreationRequestDto;
import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public interface ProductService {
    ProductDto getProductById(UUID id);

    ProductDto createProduct(ProductCreationRequestDto creationRequestDto);

    ProductDto changeProductManager(UUID uuid, NewManagerForProductRequestDto newManagerForProductRequestDto);

    ProductDto deleteProduct(UUID uuid);

    Set<ProductDto> getAllActiveProducts();
}
