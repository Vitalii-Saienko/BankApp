package com.example.bankapp.service.util;

import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ClientUpdateInfoRequestDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface ClientService {
    ClientDto getClientById(UUID id);

    ClientDto updateClient(ClientUpdateInfoRequestDto clientDto);

    ClientDto deleteClient(UUID uuid);

    ClientDto createClient(ClientCreationRequestDto creationRequestDto);

    Set<ClientDto> getManagerClients(UUID uuid);
}
