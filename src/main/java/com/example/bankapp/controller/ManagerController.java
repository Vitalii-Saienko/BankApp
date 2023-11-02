package com.example.bankapp.controller;

import com.example.bankapp.dto.ClientDto;
import com.example.bankapp.dto.ManagerRequestDto;
import com.example.bankapp.dto.ManagerDto;
import com.example.bankapp.service.util.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;


    @GetMapping("/{id}")
    public ManagerDto getManagerById(@PathVariable("id") String id){
        return managerService.getManagerById(UUID.fromString(id));
    }

    @GetMapping("/get-clients/{id}")
    public Set<ClientDto> getManagerClients(@PathVariable("id") String id){
        return managerService.getManagerClients(UUID.fromString(id));
    }

    @PostMapping("/create")
    public ManagerDto createManager(@Valid @RequestBody ManagerRequestDto creationRequestDto){
        return managerService.createManager(creationRequestDto);
    }

    @PutMapping("/update/{id}")
    public ManagerDto updateManagerInfo(@PathVariable("id") String id, @Valid @RequestBody ManagerRequestDto managerDto){
        return managerService.updateManagerInfo(UUID.fromString(id), managerDto);
    }

    @DeleteMapping("/delete/{id}")
    public ManagerDto deleteManager(@PathVariable("id") String id){
        return managerService.deleteManager(UUID.fromString(id));
    }

}
