package com.example.bankapp.service.util;

import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;

import java.util.Optional;
import java.util.UUID;

public interface AgreementService {
    AgreementDto getAgreementById(UUID id);

    AgreementDto createAgreement(AgreementCreationRequestDto agreementCreationRequestDto);

    AgreementDto changeAgreementStatusToBlocked(UUID uuid);

    AgreementDto deleteAgreement(UUID uuid);
}
