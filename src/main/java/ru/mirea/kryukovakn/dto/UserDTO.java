package ru.mirea.kryukovakn.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
}
