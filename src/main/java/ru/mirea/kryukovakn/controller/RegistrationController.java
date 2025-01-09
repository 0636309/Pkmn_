package ru.mirea.kryukovakn.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


import ru.mirea.kryukovakn.dto.UserDTO;

import java.util.Collections;

@RestController
public class RegistrationController {

    private JdbcUserDetailsManager jdbcUserDetailsManager;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        String username = userDTO.getUsername();
        String role = "ROLE_USER";

        UserDTO newUser = new UserDTO(username, encodedPassword,
                Collections.singletonList(new SimpleGrantedAuthority(role)));

        jdbcUserDetailsManager.createUser(newUser);

        return ResponseEntity.ok("Успешная регистрация.");
    }
}

