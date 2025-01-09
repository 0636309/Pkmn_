package ru.mirea.kryukovakn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.mirea.kryukovakn.dto.UserDTO;
import ru.mirea.kryukovakn.filter.JwtAuthenticationFilter;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtAuthenticationFilter jwtService;

    @Override
    public String loginUser(UserDTO userDTO) throws CredentialException {
        if (!jdbcUserDetailsManager.userExists(userDTO.getUsername())) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername(userDTO.getUsername());
        if (!passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            throw new CredentialException("Данные пользователя неправильные");
        }

        return jwtService.createToken(userDetails.getUsername(), userDetails.getAuthorities().iterator().next());
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        if (jdbcUserDetailsManager.userExists(userDTO.getUsername())) {
            throw new UsernameNotFoundException("Пользователь с данным именем уже существует");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserDTO user = new UserDTO(userDTO.getUsername(), encodedPassword, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        jdbcUserDetailsManager.createUser(user);
    }
}
