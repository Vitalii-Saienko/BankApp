package com.example.bankapp.service.impl;

import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.dto.ManagerRequestDto;
import com.example.bankapp.entity.Manager;
import com.example.bankapp.entity.enums.ManagerStatus;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.ClientMapper;
import com.example.bankapp.mapper.ManagerMapper;
import com.example.bankapp.repository.ManagerRepository;
import com.example.bankapp.service.util.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final ClientMapper clientMapper;
    private static final String EXCEPTION_MESSAGE_MANAGER = "Manager not found with ID: ";

    @Override
    public ManagerDto getManagerById(UUID id) {
        return managerMapper.managerToManagerDto(managerRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + id)));
    }

    @Transactional
    @Override
    public Set<ClientDto> getManagerClients(UUID uuid) {
        Optional<Manager> manager = managerRepository.findById(uuid);
        if (manager.isPresent()) {
            return clientMapper.clientsToClientDto(manager.get().getClientSet());
        } else {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + uuid);
        }
    }

    @Override
    public ManagerDto createManager(ManagerRequestDto creationRequestDto) {
        Manager manager = new Manager();
        manager.setManagerId(UUID.randomUUID());
        manager.setFirstName(creationRequestDto.getFirstName());
        manager.setLastName(creationRequestDto.getLastName());
        manager.setClientSet(new HashSet<>());
        manager.setManagerStatus(ManagerStatus.ACTIVE);
        manager.setCreatedAt(new Timestamp(new Date().getTime()));
        manager.setUpdatedAt(new Timestamp(new Date().getTime()));
        managerRepository.save(manager);
        return managerMapper.managerToManagerDto(manager);
    }

    @Override
    public ManagerDto updateManagerInfo(UUID uuid, ManagerRequestDto managerDto) {
        Optional<Manager> manager = managerRepository.findById(uuid);
        if (manager.isPresent()) {
            manager.get().setFirstName(managerDto.getFirstName());
            manager.get().setLastName(managerDto.getLastName());
            managerRepository.save(manager.get());
            return managerMapper.managerToManagerDto(manager.get());
        } else {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + uuid);
        }
    }

    @Override
    public ManagerDto deleteManager(UUID uuid) {
        Optional<Manager> manager = managerRepository.findById(uuid);
        if (manager.isPresent()) {
            manager.get().setManagerStatus(ManagerStatus.BLOCKED);
            managerRepository.save(manager.get());
            return managerMapper.managerToManagerDto(manager.get());
        } else {
            throw new DatabaseAccessException(EXCEPTION_MESSAGE_MANAGER + uuid);
        }
    }
}
