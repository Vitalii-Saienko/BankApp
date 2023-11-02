package com.example.bankapp.service.impl;

import com.example.bankapp.dto.NewManagerForProductRequestDto;
import com.example.bankapp.dto.ProductCreationRequestDto;
import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.Product;
import com.example.bankapp.entity.enums.Currency;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.ProductMapper;
import com.example.bankapp.repository.ManagerRepository;
import com.example.bankapp.repository.ProductRepository;
import com.example.bankapp.service.util.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ManagerRepository managerRepository;
    private static final String EXCEPTION_MESSAGE_PRODUCT = "Product not found with ID: ";
    private static final String EXCEPTION_MESSAGE_MANAGER = "Manager not found with ID: ";

    @Override
    public ProductDto getProductById(UUID id){
        return productMapper.productToProductDto(productRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_PRODUCT + id)));
    }

    @Transactional
    @Override
    public ProductDto createProduct(ProductCreationRequestDto creationRequestDto){
        Product product = new Product();
        Optional<Manager> manager = managerRepository.findById(UUID.fromString(creationRequestDto.getManagerId()));
        if (manager.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + creationRequestDto.getManagerId());
        } else {
            product.setProductId(UUID.randomUUID());
            product.setManagerId(manager.get());
            product.setAgreementSet(new HashSet<>());
            product.setProductName(creationRequestDto.getProductName());
            product.setProductStatus(Status.ACTIVE);
            product.setCurrencyCode(Currency.valueOf(creationRequestDto.getCurrencyCode()));
            product.setInterestRate(BigDecimal.valueOf(Double.parseDouble(creationRequestDto.getInterestRate())));
            product.setProductLimit(BigDecimal.valueOf(Integer.parseInt(creationRequestDto.getProductLimit())));
            product.setCreatedAt(new Timestamp(new Date().getTime()));
            product.setUpdatedAt(new Timestamp(new Date().getTime()));
            managerRepository.save(manager.get());
            productRepository.save(product);
            return productMapper.productToProductDto(product);
        }
    }

    @Transactional
    @Override
    public ProductDto changeProductManager(UUID uuid, NewManagerForProductRequestDto newManagerForProductRequestDto){
        Optional<Manager> newManager = managerRepository.findById(UUID.fromString(newManagerForProductRequestDto.getNewManagerId()));
        Optional<Product> product = productRepository.findById(uuid);
        if (newManager.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + newManagerForProductRequestDto.getNewManagerId());
        } else if (product.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_PRODUCT + uuid);
        } else {
            product.get().setManagerId(newManager.get());
            managerRepository.save(newManager.get());
            productRepository.save(product.get());
            return productMapper.productToProductDto(product.get());
        }
    }

    @Override
    public ProductDto deleteProduct(UUID uuid){
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_PRODUCT + uuid);
        } else {
            product.get().setProductStatus(Status.REMOVED);
            productRepository.save(product.get());
            return productMapper.productToProductDto(product.get());
        }
    }

    @Override
    public Set<ProductDto> getAllActiveProducts() {
        Iterable<Product> productIterable = productRepository.findAll();
        Set<Product> activeProducts = new HashSet<>();
        productIterable.forEach(activeProducts::add);
        Set<Product> collected = activeProducts.stream().filter(product -> product.getProductStatus().equals(Status.ACTIVE)).collect(Collectors.toSet());
        return productMapper.productsToProductDto(collected);
    }
}
