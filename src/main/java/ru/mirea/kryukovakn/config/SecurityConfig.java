package ru.mirea.kryukovakn.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mirea.kryukovakn.filter.JwtAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                customizer -> customizer
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/cards/**",
                                "/api/v1/students/**",
                                "/api/v1/cards/card/image")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/cards",
                                "/api/v1/students")
                        .hasRole("ADMIN")
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        .anyRequest().authenticated()
        );
        http.formLogin(form -> form.successForwardUrl("/auth/success"));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.userDetailsService(userDetailsService);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


