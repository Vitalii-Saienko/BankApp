package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.NewManagerForProductRequestDto;
import com.example.bankapp.dto.ProductCreationRequestDto;
import com.example.bankapp.dto.ProductDto;
import com.example.bankapp.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void getProductByIdTest() {
        UUID productId = UUID.fromString("29f5b116-d35f-1634-8e4a-40eb48a63f34");
        ProductDto productDto = productService.getProductById(productId);
        Assertions.assertEquals(productId.toString(), productDto.getProductId());
    }

    @Transactional
    @Rollback
    @Test
    void createProductTest() {
        ProductCreationRequestDto productCreationRequestDto = new ProductCreationRequestDto();
        productCreationRequestDto.setProductLimit("100000");
        productCreationRequestDto.setProductName("NewBankProduct");
        productCreationRequestDto.setCurrencyCode("USD");
        productCreationRequestDto.setInterestRate("2.5");
        productCreationRequestDto.setManagerId("f1b872b6-eb29-4cb9-bf0f-36f57ad39560");
        ProductDto productDto = productService.createProduct(productCreationRequestDto);
        Assertions.assertEquals("NewBankProduct", productDto.getProductName());
    }

    @Transactional
    @Rollback
    @Test
    void changeProductManagerTest() {
        UUID productId = UUID.fromString("29f5b116-d35f-1634-8e4a-40eb48a63f34");
        NewManagerForProductRequestDto newManagerForProductRequestDto = new NewManagerForProductRequestDto();
        String newManagerId = "f1b872b6-eb29-4cb9-bf0f-36f57ad39560";
        newManagerForProductRequestDto.setNewManagerId(newManagerId);
        ProductDto productDto = productService.changeProductManager(productId, newManagerForProductRequestDto);
        Assertions.assertEquals(newManagerId, productDto.getManagerId());
    }

    @Transactional
    @Rollback
    @Test
    void deleteProductTest() {
        UUID productId = UUID.fromString("29f5b116-d35f-1634-8e4a-40eb48a63f34");
        ProductDto productDto = productService.deleteProduct(productId);
        Assertions.assertEquals(Status.REMOVED.name(), productDto.getProductStatus());
    }

    @Test
    void getAllActiveProductsTest() {
       Set<ProductDto> productDto = productService.getAllActiveProducts();
       if (!productDto.isEmpty()) {
           for (ProductDto dto : productDto) {
               Assertions.assertEquals(Status.ACTIVE.name(), dto.getProductStatus());
           }
       }
    }
}