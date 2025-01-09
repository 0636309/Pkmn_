package ru.mirea.kryukovakn.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.kryukovakn.models.LoginRequest;
import ru.mirea.kryukovakn.services.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import ru.mirea.kryukovakn.services.LoginService;
import ru.mirea.kryukovakn.services.RegistrationService;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    private final LoginService loginService;

    private final RegistrationService registrationController;

    private final JdbcUserDetailsManager jdbcUserDetailsManager;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws CredentialException {
        if (!jdbcUserDetailsManager.userExists(loginRequest.getUsername())) {
            return ResponseEntity.ok("User should be registered");
        }
        String jwt = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest) {
        registrationController.registerUser(loginRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/success")
    public ResponseEntity<String> success(@AuthenticationPrincipal UserDetails user, HttpServletResponse response) throws IOException {
        log.info("Authentificated user {}", user.getUsername());
        String token = jwtTokenService.createToken(user.getUsername(), user.getAuthorities().iterator().next());
        log.info("Create jwt token for user {}", token);
        response.addCookie(new Cookie("jwt", Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8))));
        ClassPathResource resource = new ClassPathResource("success.html");
        String success = new String(Files.readAllBytes(resource.getFile().toPath()));
        return ResponseEntity.ok()
                .body(success);
    }
}

