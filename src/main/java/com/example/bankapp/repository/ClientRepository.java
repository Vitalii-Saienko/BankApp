package com.example.bankapp.repository;

import com.example.bankapp.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {
}
