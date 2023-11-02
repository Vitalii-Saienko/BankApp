package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.NewManagerForProductRequestDto;
import com.example.bankapp.dto.ProductCreationRequestDto;
import com.example.bankapp.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductServiceImpl productService;

    @Autowired
    ProductController productController;

    @Test
    void getProductByIdTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/product/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getProductByIdTestInvalidRequest() {
        String id = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/product/" + id))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createProductTestValidRequest() {
        ProductCreationRequestDto creationRequestDto = new ProductCreationRequestDto();
        creationRequestDto.setProductLimit("100");
        creationRequestDto.setProductName("test");
        creationRequestDto.setCurrencyCode("USD");
        creationRequestDto.setManagerId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        creationRequestDto.setInterestRate("2.0");

        try {
            mockMvc.perform(post("/product/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(creationRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createProductTestInvalidRequest() {
        ProductCreationRequestDto creationRequestDto = new ProductCreationRequestDto();
        try {
            mockMvc.perform(post("/product/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(creationRequestDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void changeProductManagerTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        NewManagerForProductRequestDto newManagerForProductRequestDto = new NewManagerForProductRequestDto();
        newManagerForProductRequestDto.setNewManagerId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        try {
            mockMvc.perform(put("/product/update/" + id)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(newManagerForProductRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void changeProductManagerTestInvalidRequest() {
        String id = "not-a-valid-uuid";
        NewManagerForProductRequestDto newManagerForProductRequestDto = new NewManagerForProductRequestDto();
        try {
            mockMvc.perform(put("/product/update/" + id)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(newManagerForProductRequestDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteProductTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/product/delete/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteProductTestInvalidRequest() {
        String id = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/product/delete/" + id))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllActiveProductsTestValidRequest() {
        try {
            mockMvc.perform(get("/product/show-all-active-products"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}