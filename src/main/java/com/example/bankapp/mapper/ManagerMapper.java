package com.example.bankapp.mapper;

import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.entity.Manager;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR)
@Component
public interface ManagerMapper {
    ManagerDto managerToManagerDto(Manager manager);
}
