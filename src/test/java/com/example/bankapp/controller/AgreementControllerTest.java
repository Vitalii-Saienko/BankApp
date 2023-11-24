package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.UserGenerator;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    @Autowired
    UserGenerator userGenerator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void getAgreementByIdTestValidId() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/agreement/" + id)
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAgreementByIdTestNotValidId() throws Exception {
        UUID invalidId = UUID.fromString("a00e0000-c00a-0f0b-0f00-00000000d000");
        Mockito.when(agreementService.getAgreementById(invalidId))
                .thenThrow(new DatabaseAccessException("Agreement not found with ID: " + invalidId));

            mockMvc.perform(get("/agreement/" + invalidId)
                    .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isBadRequest());
        }

    @Test
    void getAgreementByIdTestNotAuthenticated() throws Exception {
        mockMvc.perform(get("/agreement/" + "a10e6991-c16a-4f9b-9f04-6fda0510d611")
                .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN"))))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(authorities = {"ADMIN"})
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
    @WithMockUser(authorities = {"ADMIN"})
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
    void createAgreementTestNotAuthenticated() {
        AgreementCreationRequestDto agreementCreationRequestDto = new AgreementCreationRequestDto();
        agreementCreationRequestDto.setProductId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        agreementCreationRequestDto.setAccountId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        agreementCreationRequestDto.setAgreementSum("100");

        try {
            mockMvc.perform(post("/agreement/create")
                            .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN")))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(agreementCreationRequestDto)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void changeAgreementStatusToBlockedTestValidRequest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(put("/agreement/update/" + validUuid)
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
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
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(new AgreementCreationRequestDto())))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAgreementTestValidRequestAuthorized() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/agreement/delete/" + validUuid)
                    .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAgreementTestValidRequestAccessForbidden() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/agreement/delete/" + validUuid)
                            .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN"))))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAgreementTestNotValidRequest() {
        String validUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/agreement/delete/" + validUuid)
                    .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}