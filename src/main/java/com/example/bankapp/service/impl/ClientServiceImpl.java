package com.example.bankapp.service.impl;

import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ClientUpdateInfoRequestDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Client;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.ClientMapper;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.ClientRepository;
import com.example.bankapp.repository.ManagerRepository;
import com.example.bankapp.service.util.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ManagerRepository managerRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final AccountRepository accountRepository;
    private static final String EXCEPTION_MESSAGE_CLIENT = "Client not found with ID: ";
    private static final String EXCEPTION_MESSAGE_MANAGER = "Manager not found with ID: ";

    @Override
    public ClientDto getClientById(UUID id) {
        return clientMapper.clientToClientDto(clientRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + id)));
    }

    @Override
    public ClientDto updateClient(ClientUpdateInfoRequestDto clientDto) {
        UUID clientId = UUID.fromString(clientDto.getClientId());
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + clientDto.getClientId());
        }

        Client client = optionalClient.get();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        client.setUpdatedAt(currentTimestamp);

        clientRepository.save(client);

        return clientMapper.clientToClientDto(client);
    }

    @Override
    public ClientDto deleteClient(UUID uuid) {
        if (clientRepository.existsById(uuid)) {
            Client client =
                    clientRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + uuid));
            client.setClientStatus(Status.REMOVED);
            deleteClientAccounts(client);
            clientRepository.save(client);
        }
        return clientMapper.clientToClientDto(clientRepository.findById(uuid)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + uuid)));
    }

    private void deleteClientAccounts(Client client) {
        for (Account account : client.getAccountSet()) {
            accountServiceImpl.updateAccountStatus(account.getAccountUUID(), new AccountStatusDto("REMOVED"));
            accountRepository.save(account);
        }
    }

    @Transactional
    @Override
    public ClientDto createClient(ClientCreationRequestDto creationRequestDto) {
        Optional<Manager> manager = managerRepository.findById(UUID.fromString(creationRequestDto.getManagerId()));
        if (manager.isEmpty()) {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + creationRequestDto.getManagerId());
        } else {
            Client client = new Client();
            client.setClientId(UUID.randomUUID());
            client.setManagerId(manager.get());
            client.setAccountSet(new HashSet<>());
            client.setClientStatus(Status.ACTIVE);
            client.setTaxCode(creationRequestDto.getTaxCode());
            client.setFirstName(creationRequestDto.getFirstName());
            client.setLastName(creationRequestDto.getLastName());
            client.setEmail(creationRequestDto.getEmail());
            client.setAddress(creationRequestDto.getAddress());
            client.setPhone(creationRequestDto.getPhone());
            client.setCreatedAt(new Timestamp(new Date().getTime()));
            client.setUpdatedAt(new Timestamp(new Date().getTime()));
            managerRepository.save(manager.get());
            clientRepository.save(client);
            return clientMapper.clientToClientDto(client);
        }
    }

    @Transactional
    @Override
    public Set<ClientDto> getManagerClients(UUID uuid) {
        Manager manager =
                managerRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + uuid));
        return clientMapper.clientsToClientDto(manager.getClientSet());
    }
}
