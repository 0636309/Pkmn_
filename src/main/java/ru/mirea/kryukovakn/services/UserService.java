package ru.mirea.kryukovakn.services;

import ru.mirea.kryukovakn.dto.UserDTO;

import javax.security.auth.login.CredentialException;

public interface UserService {
    String loginUser(UserDTO userDTO) throws CredentialException;
    void registerUser(UserDTO userDTO);
}
