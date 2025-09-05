package com.konekt.backend.konket_backend.Services.UserService;

import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updatePhotoProfile(String idUser, String urlPhoto) {
        Optional<User> userOptional = userRepository.findById(idUser);
        User userBd = userOptional.orElseThrow();
        userBd.setProfilePictureUrl(urlPhoto);
        return userRepository.save(userBd);
    }
}
