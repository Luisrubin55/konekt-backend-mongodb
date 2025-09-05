package com.konekt.backend.konket_backend.Services.UserService;

import com.konekt.backend.konket_backend.Entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> getUserByEmail(String email);
    User updatePhotoProfile(String idUser,String urlPhoto);
}
