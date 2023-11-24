package com.example.bankapp;

import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.UserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class BankAppApplicationTests {

    @Autowired
    UserGenerator userGenerator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {

    }

    @Test
    void test() {
        assertTrue(true);
    }
}
