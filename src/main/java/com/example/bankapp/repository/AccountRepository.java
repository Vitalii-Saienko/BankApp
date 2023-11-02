package com.example.bankapp.repository;

import com.example.bankapp.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository <Account, UUID> {
}
