package ru.mirea.kryukovakn.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.mirea.kryukovakn.services.JwtTokenService;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter {
    @Autowired
    private final JwtTokenService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        log.info("From request {}", jwt);

        if (Objects.isNull(jwt) || !jwt.startsWith("Bearer")) {
            if (!Objects.isNull(request.getCookies())) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("jwt")) {
                        jwt = new String(Base64.getDecoder().decode(cookie.getValue()));
                        log.info("From cookie {}", jwt);
                    }
                }
            }
            if (Objects.isNull(jwt) || jwt.startsWith("Basic")) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }

        DecodedJWT decodedJWT = jwtService.verify(jwt);

        if (Objects.isNull(decodedJWT)) {
            filterChain.doFilter(request, response);
            return;
        }

        var authoritiesClaim = decodedJWT.getClaim("authorities");
        if (authoritiesClaim != null && !authoritiesClaim.isNull()) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            decodedJWT.getSubject(),
                            null,
                            decodedJWT.getClaim("authorities").asList(SimpleGrantedAuthority.class)
                    )
            );
        }
        else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }
}
