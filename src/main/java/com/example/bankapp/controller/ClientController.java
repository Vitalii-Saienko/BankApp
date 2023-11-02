package com.example.bankapp.controller;

import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ClientUpdateInfoRequestDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.service.util.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable("id") String id){
        return clientService.getClientById(UUID.fromString(id));
    }

    @PutMapping("/update")
    public ClientDto updateClient(@Valid @RequestBody ClientUpdateInfoRequestDto clientDto){
        return clientService.updateClient(clientDto);
    }

    @DeleteMapping("/delete/{id}")
    public ClientDto deleteClient(@PathVariable("id") String id){
        return clientService.deleteClient(UUID.fromString(id));
    }

    @PostMapping("/create")
    public ClientDto createClient(@Valid @RequestBody ClientCreationRequestDto creationRequestDto){
        return clientService.createClient(creationRequestDto);
    }

    @GetMapping("/show-clients-belongs-to-manager/{id}")
    public Set<ClientDto> getManagerClients(@PathVariable("id") String id){
        return clientService.getManagerClients(UUID.fromString(id));
    }

}
