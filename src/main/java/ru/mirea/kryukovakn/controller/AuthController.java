package ru.mirea.kryukovakn.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.kryukovakn.services.JwtTokenService;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Base64;

@Controller
@RequestMapping("/success")
@AllArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;


    @PostMapping
    public void handleSuccess(Authentication authentication, HttpServletResponse response) {
        String username = authentication.getName();
        var authorities = authentication.getAuthorities();

        String jwt = jwtTokenService.createToken(username, authorities);

        String encodedJwt = Base64.getEncoder().encodeToString(jwt.getBytes());

        Cookie cookie = new Cookie("JWT_TOKEN", encodedJwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);
    }
}

