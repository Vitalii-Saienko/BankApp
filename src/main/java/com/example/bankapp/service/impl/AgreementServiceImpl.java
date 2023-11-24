package com.example.bankapp.service.impl;

import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Agreement;
import com.example.bankapp.entity.Product;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.AgreementMapper;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.AgreementRepository;
import com.example.bankapp.repository.ProductRepository;
import com.example.bankapp.service.util.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private static final String EXCEPTION_MESSAGE_AGREEMENT = "Agreement not found with ID: ";

    @Override
    public AgreementDto getAgreementById(UUID id) {
        return agreementMapper.agreementToAgreementDto(agreementRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_AGREEMENT + id)));
    }

    @Transactional
    @Override
    public AgreementDto createAgreement(AgreementCreationRequestDto agreementCreationRequestDto) {
        Agreement agreement = new Agreement();
        Product product =
                productRepository.findById(UUID.fromString(agreementCreationRequestDto.getProductId())).orElseThrow(() -> new DatabaseAccessException("Product not found with ID: " + agreementCreationRequestDto.getProductId()));
        Account account =
                accountRepository.findById(UUID.fromString(agreementCreationRequestDto.getAccountId())).orElseThrow(() -> new DatabaseAccessException("Account not found with ID: " + agreementCreationRequestDto.getAccountId()));
        agreement.setAgreementId(UUID.randomUUID());
        agreement.setAccountId(account);
        agreement.setProductId(product);
        agreement.setInterestRate(product.getInterestRate());
        agreement.setAgreementStatus(Status.ACTIVE);
        int sum = Integer.parseInt(agreementCreationRequestDto.getAgreementSum());
        agreement.setAgreementSum(sum <= product.getProductLimit().intValue() ? new BigDecimal(sum) :
                product.getProductLimit());
        agreement.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        agreement.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);
        accountRepository.save(account);
        agreementRepository.save(agreement);
        return agreementMapper.agreementToAgreementDto(agreement);
    }

    @Override
    public AgreementDto changeAgreementStatusToBlocked(UUID uuid) {
        Agreement agreement =
                agreementRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_AGREEMENT + uuid));
        agreement.setAgreementStatus(Status.BLOCKED);
        agreementRepository.save(agreement);
        return agreementMapper.agreementToAgreementDto(agreement);
    }

    @Override
    public AgreementDto deleteAgreement(UUID uuid) {
        Agreement agreement =
                agreementRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_AGREEMENT + uuid));
        agreement.setAgreementStatus(Status.REMOVED);
        agreementRepository.save(agreement);
        return agreementMapper.agreementToAgreementDto(agreement);
    }
}
