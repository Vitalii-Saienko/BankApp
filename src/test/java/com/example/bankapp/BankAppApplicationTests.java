package com.example.bankapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class BankAppApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        assertTrue(true);
    }
}
