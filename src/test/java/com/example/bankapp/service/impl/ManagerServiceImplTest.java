package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ManagerRequestDto;
import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.entity.enums.ManagerStatus;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ManagerServiceImplTest {

    @Autowired
    private ManagerServiceImpl managerService;

    @Test
    void getManagerByIdTestExistUUID() {
        UUID managerId = UUID.fromString("6c0c5b68-bf0d-4646-a63d-63f4a79312d3");
        ManagerDto managerById = managerService.getManagerById(managerId);
        Assertions.assertEquals("Oksana", managerById.getFirstName());
    }

    @Test
    void getManagerByIdTestNotExistUUID() {
        UUID managerId = UUID.fromString("0c0c0b00-bf0d-0000-a00d-00f0a00000d0");
        Assertions.assertThrows(DatabaseAccessException.class, () -> managerService.getManagerById(managerId));
    }

    @Test
    void getManagerClientsTest() {
        UUID managerId = UUID.fromString("6c0c5b68-bf0d-4646-a63d-63f4a79312d3");
        Set<ClientDto> managerClients = managerService.getManagerClients(managerId);
        Assertions.assertEquals(2, managerClients.size());
    }

    @Transactional
    @Rollback
    @Test
    void createManagerTest() {
        ManagerRequestDto creationRequestDto = new ManagerRequestDto();
        creationRequestDto.setFirstName("NewManagerFirstName");
        creationRequestDto.setLastName("NewManagerLastName");

        ManagerDto managerDto = managerService.createManager(creationRequestDto);

        Assertions.assertEquals("NewManagerFirstName", managerDto.getFirstName());
    }

    @Transactional
    @Rollback
    @Test
    void updateManagerInfoTest() {
        UUID managerId = UUID.fromString("6c0c5b68-bf0d-4646-a63d-63f4a79312d3");
        ManagerRequestDto updateRequestDto = new ManagerRequestDto();
        updateRequestDto.setFirstName("ChangedFirstName");
        updateRequestDto.setLastName("ChangedLastName");
        ManagerDto updateManagerInfo = managerService.updateManagerInfo(managerId, updateRequestDto);
        Assertions.assertEquals("ChangedFirstName", updateManagerInfo.getFirstName());
    }

    @Transactional
    @Rollback
    @Test
    void deleteManagerTest() {
        UUID managerId = UUID.fromString("6c0c5b68-bf0d-4646-a63d-63f4a79312d3");
        ManagerDto managerDto = managerService.deleteManager(managerId);
        Assertions.assertEquals(ManagerStatus.BLOCKED.name(), managerDto.getManagerStatus());
    }
}