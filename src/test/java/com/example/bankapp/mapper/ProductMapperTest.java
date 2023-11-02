package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.Product;
import com.example.bankapp.entity.enums.Currency;
import com.example.bankapp.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void productToProductDtoTest() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setProductName("Test");
        product.setProductLimit(BigDecimal.valueOf(1000));
        product.setManagerId(new Manager());
        product.setProductStatus(Status.ACTIVE);
        product.setAgreementSet(new HashSet<>());
        product.setCurrencyCode(Currency.CHF);
        product.setInterestRate(BigDecimal.valueOf(0.1));

        ProductDto productDto = productMapper.productToProductDto(product);

        Assertions.assertEquals(product.getProductId().toString(), productDto.getProductId());
    }

    @Test
    void productsToProductDtoTest() {
        Set <Product> products = new HashSet<>();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setProductName("Test");
        product.setProductLimit(BigDecimal.valueOf(1000));
        product.setProductStatus(Status.ACTIVE);
        product.setAgreementSet(new HashSet<>());
        product.setCurrencyCode(Currency.CHF);
        product.setInterestRate(BigDecimal.valueOf(0.1));

        Manager manager = new Manager();
        manager.setManagerId(UUID.randomUUID());
        product.setManagerId(manager);
        products.add(product);

        Set<ProductDto> productDto = productMapper.productsToProductDto(products);

        Assertions.assertEquals(products.size(), productDto.size());
    }
}