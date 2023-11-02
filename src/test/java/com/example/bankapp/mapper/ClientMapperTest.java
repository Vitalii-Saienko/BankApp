package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.entity.Client;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ClientMapperTest {

    @Autowired
    ClientMapper clientMapper;

    @Test
    void clientToClientDto() {
        Client client = new Client();
        client.setClientId(UUID.randomUUID());
        client.setClientStatus(Status.ACTIVE);
        client.setPhone("123");
        client.setTaxCode("1234");
        client.setFirstName("John");
        client.setManagerId(new Manager());
        client.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        client.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        client.setEmail("a@b.com");
        client.setAddress("address");
        client.setLastName("Johnson");
        client.setAccountSet(new HashSet<>());

        ClientDto clientDto = clientMapper.clientToClientDto(client);

        Assertions.assertEquals(client.getClientId().toString(), clientDto.getClientId());
    }

    @Test
    void clientsToClientDto() {
        Set<Client> clients = new HashSet<>();
        clients.add(new Client());

        Set<ClientDto> clientDto = clientMapper.clientsToClientDto(clients);

        Assertions.assertEquals(1, clientDto.size());
    }
}