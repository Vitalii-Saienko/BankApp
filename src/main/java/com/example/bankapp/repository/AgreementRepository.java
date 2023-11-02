package com.example.bankapp.repository;

import com.example.bankapp.entity.Agreement;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AgreementRepository extends CrudRepository<Agreement, UUID> {
}
