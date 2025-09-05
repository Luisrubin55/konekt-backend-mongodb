package com.konekt.backend.konket_backend.Services.Authentication;

import com.konekt.backend.konket_backend.Entities.DTO.UserRequestDTO;
import com.konekt.backend.konket_backend.Entities.User;

import java.util.Optional;

public interface IAuthenticateService {
    User AddNewUser(UserRequestDTO user);
    Boolean validateToken(String token);
    Optional<User> recoverPasswordToken(String email);
    Optional<User> recoverPassword(String token, String password);
}
