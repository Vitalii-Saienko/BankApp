package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.enums.ManagerStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class ManagerMapperTest {

    @Autowired
    private ManagerMapper managerMapper;

    @Test
    void managerToManagerDto() {
        Manager manager = new Manager();
        manager.setManagerId(UUID.randomUUID());
        manager.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        manager.setManagerStatus(ManagerStatus.ACTIVE);
        manager.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        manager.setLastName("Bob");
        manager.setLastName("Robson");
        manager.setClientSet(new HashSet<>());
        manager.setProductSet(new HashSet<>());

        ManagerDto managerDto = managerMapper.managerToManagerDto(manager);

        Assertions.assertEquals(manager.getManagerId().toString(), managerDto.getManagerId());
    }
}