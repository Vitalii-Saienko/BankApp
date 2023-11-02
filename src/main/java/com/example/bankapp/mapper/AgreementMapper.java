package com.example.bankapp.mapper;

import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Agreement;
import com.example.bankapp.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
@Component
public interface AgreementMapper {
    AgreementDto agreementToAgreementDto(Agreement agreement);

    default String map(Product value) {
        return String.valueOf(value.getProductId());
    }

    default String map(Account value) {
        return String.valueOf(value.getAccountUUID());
    }
}
