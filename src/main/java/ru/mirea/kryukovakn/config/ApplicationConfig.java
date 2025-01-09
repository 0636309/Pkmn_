package ru.mirea.kryukovakn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}