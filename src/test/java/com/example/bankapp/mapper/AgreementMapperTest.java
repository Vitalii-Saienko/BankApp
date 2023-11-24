package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Agreement;
import com.example.bankapp.entity.Product;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.UserGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class AgreementMapperTest {

    @Autowired
    AgreementMapper agreementMapper;


    @Test
    void agreementToAgreementDtoTest() {
        Agreement agreement = new Agreement();
        agreement.setAgreementSum(BigDecimal.valueOf(1000));
        agreement.setAgreementStatus(Status.ACTIVE);
        agreement.setAgreementId(UUID.fromString("2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b"));
        agreement.setProductId(new Product());
        agreement.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        agreement.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        agreement.setInterestRate(BigDecimal.valueOf(2.5));
        agreement.setAccountId(new Account());

        AgreementDto agreementDto = agreementMapper.agreementToAgreementDto(agreement);

        Assertions.assertEquals(agreementDto.getAgreementId(), agreement.getAgreementId().toString());
    }
}