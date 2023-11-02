package com.example.bankapp.controller;

import com.example.bankapp.dto.AgreementCreationRequestDto;
import com.example.bankapp.dto.AgreementDto;
import com.example.bankapp.service.util.AgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/agreement")
@RequiredArgsConstructor
public class AgreementController {

    private final AgreementService agreementService;

    @GetMapping("/{id}")
    public AgreementDto getAgreementById(@PathVariable("id") String id){
        return agreementService.getAgreementById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public AgreementDto createAgreement(@RequestBody @Valid AgreementCreationRequestDto agreementCreationRequestDto){
        return agreementService.createAgreement(agreementCreationRequestDto);
    }

    @PutMapping("/update/{id}")
    public AgreementDto changeAgreementStatusToBlocked(@PathVariable("id") String id){
        return agreementService.changeAgreementStatusToBlocked(UUID.fromString(id));
    }

    @DeleteMapping("/delete/{id}")
    public AgreementDto deleteAgreement(@PathVariable("id") String id){
        return agreementService.deleteAgreement(UUID.fromString(id));
    }
}
