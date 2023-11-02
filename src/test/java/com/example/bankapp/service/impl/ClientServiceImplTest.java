package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ClientCreationRequestDto;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ClientUpdateInfoRequestDto;
import com.example.bankapp.entity.enums.Status;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;
    @Test
    void getClientByIdTestExistId() {
        UUID clientId = UUID.fromString("29f5b116-d35f-4171-5e4a-40eb48a63f34");
        ClientDto clientById = clientService.getClientById(clientId);
        Assertions.assertEquals("John", clientById.getFirstName());
        Assertions.assertEquals("Lee", clientById.getLastName());
    }

    @Test
    void getClientByIdTestNotExistId() {
        UUID notExistId = UUID.fromString("00f0b000-d00f-0000-0e0a-00eb00a00f00");
        Assertions.assertThrows(DatabaseAccessException.class, () -> clientService.getClientById(notExistId));
    }

    @Transactional
    @Rollback
    @Test
    void updateClientTest() {
        UUID clientId = UUID.fromString("29f5b116-d35f-4171-5e4a-40eb48a63f34");
        ClientUpdateInfoRequestDto clientDto = new ClientUpdateInfoRequestDto();
        clientDto.setFirstName("NewName");
        clientDto.setLastName("NewLastName");
        clientDto.setAddress("NewAddress");
        clientDto.setEmail("NewEmail@gmail.com");
        clientDto.setPhone("0000000000");
        clientDto.setClientId(clientId.toString());

        ClientDto updatedClient = clientService.updateClient(clientDto);

        Assertions.assertEquals("NewName", updatedClient.getFirstName());
        Assertions.assertEquals("NewLastName", updatedClient.getLastName());
    }

    @Transactional
    @Rollback
    @Test
    void deleteClientTest() {
        UUID clientId = UUID.fromString("29f5b116-d35f-4171-5e4a-40eb48a63f34");
        ClientDto clientDto = clientService.deleteClient(clientId);
        Assertions.assertEquals(clientDto.getClientStatus(), Status.REMOVED.name());
    }

    @Transactional
    @Rollback
    @Test
    void createClientTest() {
        ClientCreationRequestDto requestDto = new ClientCreationRequestDto();
        requestDto.setFirstName("NewName");
        requestDto.setLastName("NewLastName");
        requestDto.setEmail("NewEmail@gmail.com");
        requestDto.setAddress("NewAddress");
        requestDto.setPhone("0000000000");
        requestDto.setTaxCode("1234567890");
        requestDto.setManagerId("89c9e53b-1b7c-45a4-8d35-4f5de2f088e0");

        ClientDto clientDto = clientService.createClient(requestDto);

        Assertions.assertEquals("NewName", clientDto.getFirstName());
        Assertions.assertEquals("NewLastName", clientDto.getLastName());
    }


    @Test
    void getManagerClientsTest() {
        UUID managerId = UUID.fromString("89c9e53b-1b7c-45a4-8d35-4f5de2f088e0");
        Set<ClientDto> managerClients = clientService.getManagerClients(managerId);
        Assertions.assertEquals(3, managerClients.size());
    }
}