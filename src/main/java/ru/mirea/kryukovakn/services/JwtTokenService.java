package ru.mirea.kryukovakn.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenService {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${token.secret}")
    private String SECRET_KEY;

    @Value("${token.expiration}")
    private long TOKEN_EXPIRATION_MINUTES;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(SECRET_KEY);
    }

    public String createToken(String username, GrantedAuthority authority) {
        return JWT.create()
                .withIssuer("pkmn")
                .withSubject(username)
                .withClaim("authorities", List.of(authority.getAuthority()))
                .withExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
    }

    public DecodedJWT verify(String jwt) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("pkmn")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);



            if (Objects.isNull(userDetailsService.loadUserByUsername(decodedJWT.getSubject()))) {
                log.error("Пользователь не найден");
                return null;
            }

            log.info("JWT истекает в {}", decodedJWT.getExpiresAt());
            return decodedJWT;
        }
        catch (JWTVerificationException e ) {
            log.error("Ошибка при верификации {}", e.getMessage());
            return null;
        }
    }
}
