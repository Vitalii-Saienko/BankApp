package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.service.impl.AgreementServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class AgreementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AgreementServiceImpl agreementService;

    @Autowired
    AgreementController agreementController;

    @Test
    void getAgreementByIdTestValidId() {
        UUID id = UUID.randomUUID();
        AgreementDto agreementDto = new AgreementDto();
        Mockito.when(agreementService.getAgreementById(id)).thenReturn(agreementDto);

        try {
            mockMvc.perform(get("/agreement/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAgreementByIdTestNotValidId() {
        UUID invalidId = UUID.randomUUID();
        Mockito.when(agreementService.getAgreementById(invalidId))
                .thenThrow(new DatabaseAccessException("Agreement not found with ID: " + invalidId));

        Assertions.assertThrows(DatabaseAccessException.class, () -> {
            agreementController.getAgreementById(String.valueOf(invalidId));
            mockMvc.perform(get("/agreement/" + invalidId))
                    .andExpect(status().is5xxServerError());
        });
    }

    @Test
    void createAgreementTestValidRequest() {
        AgreementCreationRequestDto agreementCreationRequestDto = new AgreementCreationRequestDto();
        agreementCreationRequestDto.setProductId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        agreementCreationRequestDto.setAccountId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        agreementCreationRequestDto.setAgreementSum("100");
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setAgreementId(String.valueOf(UUID.randomUUID()));
        Mockito.when(agreementService.createAgreement(agreementCreationRequestDto)).thenReturn(agreementDto);
        Assertions.assertEquals(agreementDto, agreementController.createAgreement(agreementCreationRequestDto));
    }

    @Test
    void createAgreementTestNotValidRequest() {
        AgreementCreationRequestDto agreementCreationRequestDto = new AgreementCreationRequestDto();
        agreementCreationRequestDto.setProductId("111");
        agreementCreationRequestDto.setAccountId("");
        agreementCreationRequestDto.setAgreementSum("a10e6991-c16a-4f9b-9f04-6fda0510d611");

        try {
            mockMvc.perform(post("/agreement/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(agreementCreationRequestDto)))
                    .andExpect(status().isBadRequest());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void changeAgreementStatusToBlockedTestValidRequest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(put("/agreement/update/" + validUuid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(new AgreementCreationRequestDto())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void changeAgreementStatusToBlockedTestInvalidRequest() {
        String invalidUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(put("/agreement/update/" + invalidUuid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(new AgreementCreationRequestDto())))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAgreementTestValidRequest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/agreement/delete/" + validUuid))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAgreementTestNotValidRequest() {
        String validUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/agreement/delete/" + validUuid))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}