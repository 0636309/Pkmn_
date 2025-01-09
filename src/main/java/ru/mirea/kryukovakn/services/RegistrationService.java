package ru.mirea.kryukovakn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.mirea.kryukovakn.dto.UserDTO;
import ru.mirea.kryukovakn.models.LoginRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final PasswordEncoder passwordEncoder;

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
