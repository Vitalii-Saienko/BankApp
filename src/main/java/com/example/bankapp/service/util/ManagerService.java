package com.example.bankapp.service.util;

import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.dto.ManagerRequestDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface ManagerService {

    ManagerDto getManagerById(UUID id);

    Set<ClientDto> getManagerClients(UUID uuid);

    ManagerDto createManager(ManagerRequestDto creationRequestDto);

    ManagerDto updateManagerInfo(UUID uuid, ManagerRequestDto managerDto);

    ManagerDto deleteManager(UUID uuid);
}
