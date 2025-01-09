package ru.mirea.kryukovakn.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mirea.kryukovakn.services.JwtTokenService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if ((Objects.isNull(token)) || !token.startsWith("Bearer ")) {
            if (Objects.isNull(request)) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("jwt"))
                        token = new String(Base64.getDecoder().decode(cookie.getValue()));
                }
            }

            if (Objects.isNull(token)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (token.startsWith("Bearer ")) {
            token = token.split("Bearer ")[1];
            log.info("JWT token: {}", token);
        }

        DecodedJWT decodedJWT = jwtTokenService.verify(token);

        if (Objects.isNull(decodedJWT)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Информация токена: {}", List.of(new SimpleGrantedAuthority(decodedJWT.getClaim("authority").asString())));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        decodedJWT.getSubject(),
                        null,
                        List.of(new SimpleGrantedAuthority(decodedJWT.getClaim("authority").asString()))
                )
        );

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        String cookieToken = getJwtFromCookie(request);
        if (cookieToken != null) {
            return decodeBase64(cookieToken);
        }

        return null;
    }
    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }


    public static class JwtAuthenticationException extends RuntimeException {
        public JwtAuthenticationException(String message) {
            super(message);
        }
    }
}


