package com.konekt.backend.konket_backend.Services.UserService;

import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Entities.UserImages;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getUserByEmail(String email);
    User updatePhotoProfile(String idUser,String urlPhoto);
    List<UserImages> getPhotosByIdUser(String idUser);
}
