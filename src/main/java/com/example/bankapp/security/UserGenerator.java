package com.example.bankapp.security;

import com.example.bankapp.entity.User;
import com.example.bankapp.entity.enums.Role;
import com.example.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserGenerator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_PASSWORD}")
    private String ADMIN_PASSWORD;

    @Value("${MANAGER_PASSWORD}")
    private String MANAGER_PASSWORD;


    public UserGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void generateInitialUsers() {
        User userAdmin = new User();
        userAdmin.setId(1L);
        userAdmin.setUsername("admin");
        userAdmin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        userAdmin.setRole(Role.ADMIN);
        userRepository.save(userAdmin);

        User userManager = new User();
        userManager.setId(2L);
        userManager.setUsername("manager");
        userManager.setPassword(passwordEncoder.encode(MANAGER_PASSWORD));
        userManager.setRole(Role.MANAGER);
        userRepository.save(userManager);
    }
}
