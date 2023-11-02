package com.example.bankapp.mapper;

import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR)
@Component
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);

    Set<ClientDto> clientsToClientDto(Set<Client> clients);

}
