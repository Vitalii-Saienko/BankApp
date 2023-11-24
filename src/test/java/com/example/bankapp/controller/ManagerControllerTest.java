package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ManagerRequestDto;
import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.UserGenerator;
import com.example.bankapp.service.impl.ManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ManagerServiceImpl managerService;

    @Autowired
    ManagerController managerController;

    @Autowired
    UserGenerator userGenerator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getManagerByIdTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/manager/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getManagerByIdTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/manager/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getManagerClientsTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/manager/get-clients/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getManagerClientsTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/manager/get-clients/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void createManagerTestValidRequest() {
        ManagerRequestDto creationRequestDto = new ManagerRequestDto();
        creationRequestDto.setFirstName("test");
        creationRequestDto.setLastName("test");
        try {
            mockMvc.perform(post("/manager/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(creationRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void createManagerTestInvalidRequest() {
        ManagerRequestDto invalidCreationRequestDto = new ManagerRequestDto();
        try {
            mockMvc.perform(post("/manager/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(invalidCreationRequestDto)))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void updateManagerInfoTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        ManagerRequestDto managerRequestDto = new ManagerRequestDto();
        managerRequestDto.setFirstName("test");
        managerRequestDto.setLastName("test");
        try {
            mockMvc.perform(put("/manager/update/" + id)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(managerRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void updateManagerInfoTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        ManagerRequestDto invalidManagerRequestDto = new ManagerRequestDto();
        try {
            mockMvc.perform(put("/manager/update/" + invalidId)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(invalidManagerRequestDto)))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void deleteManagerTestValidRequest() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/manager/delete/" + id))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"INTERN"})
    void deleteManagerTestValidRequestAccessDenied() {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/manager/delete/" + id))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void deleteManagerTestInvalidRequest() {
        String invalidId = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/manager/delete/" + invalidId))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}