package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.service.impl.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientServiceImpl clientService;

    @Autowired
    ClientController clientController;

    @Test
    void getClientByIdTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/client/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getClientByIdTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/client/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateClientTestValidRequest() {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        clientDto.setFirstName("test");
        clientDto.setLastName("test");
        clientDto.setEmail("test@gmail.com");
        clientDto.setAddress("test");
        clientDto.setPhone("test");
        clientDto.setClientStatus("test");
        clientDto.setTaxCode("test");
        clientDto.setCreatedAt(String.valueOf(new Timestamp(new Date().getTime())));
        clientDto.setUpdatedAt(String.valueOf(new Timestamp(new Date().getTime())));
        try {
            mockMvc.perform(put("/client/update")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(clientDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateClientTestInvalidRequest() {
        ClientDto notValidClientDto = new ClientDto();
        try {
            mockMvc.perform(put("/client/update")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(notValidClientDto)))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteClientTestValidRequest() {
        String validId = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/client/delete/" + validId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteClientTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/client/delete/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createClientTestValidRequest() {
        ClientCreationRequestDto clientCreationRequestDto = new ClientCreationRequestDto();
        clientCreationRequestDto.setFirstName("test");
        clientCreationRequestDto.setLastName("test");
        clientCreationRequestDto.setEmail("test@gmail.com");
        clientCreationRequestDto.setAddress("test");
        clientCreationRequestDto.setPhone("test");
        clientCreationRequestDto.setTaxCode("test");
        clientCreationRequestDto.setManagerId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        try {
            mockMvc.perform(post("/client/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(clientCreationRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createClientTestInvalidRequest() {
        ClientCreationRequestDto notValidClientCreationRequestDto = new ClientCreationRequestDto();
        try {
            mockMvc.perform(post("/client/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(notValidClientCreationRequestDto)))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getManagerClientsTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/client/show-clients-belongs-to-manager/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getManagerClientsTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/client/show-clients-belongs-to-manager/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}