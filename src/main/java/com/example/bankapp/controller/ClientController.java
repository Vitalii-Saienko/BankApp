package com.example.bankapp.controller;

import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ClientUpdateInfoRequestDto;
import com.example.bankapp.service.util.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ClientDto getClientById(@PathVariable("id") String id) {
        return clientService.getClientById(UUID.fromString(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ClientDto updateClient(@Valid @RequestBody ClientUpdateInfoRequestDto clientDto) {
        return clientService.updateClient(clientDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ClientDto deleteClient(@PathVariable("id") String id) {
        return clientService.deleteClient(UUID.fromString(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ClientDto createClient(@Valid @RequestBody ClientCreationRequestDto creationRequestDto) {
        return clientService.createClient(creationRequestDto);
    }

    @GetMapping("/show-clients-belongs-to-manager/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public Set<ClientDto> getManagerClients(@PathVariable("id") String id) {
        return clientService.getManagerClients(UUID.fromString(id));
    }

}
