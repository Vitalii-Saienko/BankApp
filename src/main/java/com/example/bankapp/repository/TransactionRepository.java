package com.example.bankapp.repository;

import com.example.bankapp.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}
