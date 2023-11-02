package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class AgreementServiceImplTest {

    @Autowired
    private AgreementServiceImpl agreementService;

    @Test
    void getAgreementByIdTest() {
        String agreementId = "102032a7-8cd6-4e17-33b0-78d5aa11b3b1";
        AgreementDto agreementDto = agreementService.getAgreementById(UUID.fromString(agreementId));
        Assertions.assertEquals("14500.00", agreementDto.getAgreementSum());
        Assertions.assertEquals("a10e6991-c16a-4f9b-9f04-6fda8192d61e", agreementDto.getAccountId());
    }

    @Transactional
    @Rollback
    @Test
    void createAgreementTest() {
        AgreementCreationRequestDto agreementCreationRequestDto = new AgreementCreationRequestDto();
        agreementCreationRequestDto.setAgreementSum("11111");
        agreementCreationRequestDto.setAccountId("a10e6991-c16a-4f9b-9f04-6fda8192d61e");
        agreementCreationRequestDto.setProductId("672032a7-8cd6-4e16-34b0-78d5aa11b310");
        AgreementDto agreementDto = agreementService.createAgreement(agreementCreationRequestDto);
        Assertions.assertEquals("11111", agreementDto.getAgreementSum());
        Assertions.assertEquals("a10e6991-c16a-4f9b-9f04-6fda8192d61e", agreementDto.getAccountId());
    }

    @Transactional
    @Rollback
    @Test
    void changeAgreementStatusToBlockedTest() {
        UUID agreementId = UUID.fromString("49f5b116-d35f-4173-3e4a-40eb48a63f38");
        AgreementDto agreementDto = agreementService.changeAgreementStatusToBlocked(agreementId);
        Assertions.assertEquals(Status.BLOCKED.name(), agreementDto.getAgreementStatus());
    }

    @Transactional
    @Rollback
    @Test
    void deleteAgreementTest() {
        UUID agreementId = UUID.fromString("49f5b116-d35f-4173-3e4a-40eb48a63f38");
        AgreementDto agreementDto = agreementService.deleteAgreement(agreementId);
        Assertions.assertEquals(Status.REMOVED.name(), agreementDto.getAgreementStatus());
    }
}