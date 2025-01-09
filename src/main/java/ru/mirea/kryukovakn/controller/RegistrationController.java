package ru.mirea.kryukovakn.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


import ru.mirea.kryukovakn.dto.UserDTO;
import ru.mirea.kryukovakn.models.LoginRequest;

import java.util.Collections;
import java.util.List;

@RestController
public class RegistrationController {

    private JdbcUserDetailsManager jdbcUserDetailsManager;

    private PasswordEncoder passwordEncoder;


    public ResponseEntity<String> registerUser(LoginRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserDTO newUser = UserDTO.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .enabled(true)
                .build();
        jdbcUserDetailsManager.createUser(newUser);

        return ResponseEntity.ok("Успешная регистрация.");
    }
}

