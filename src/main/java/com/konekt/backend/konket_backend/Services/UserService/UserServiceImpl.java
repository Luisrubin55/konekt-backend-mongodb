package com.konekt.backend.konket_backend.Services.UserService;

import com.konekt.backend.konket_backend.Entities.DTO.UserRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.UserResponseFeedDTO;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Entities.UserImages;
import com.konekt.backend.konket_backend.Repositories.UserImagesRepository;
import com.konekt.backend.konket_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserImagesRepository userImagesRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserResponseFeedDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updatePhotoProfile(String idUser, String urlPhoto) {
        User userBd = userRepository.findById(idUser).orElseThrow();
        UserImages userImages = new UserImages();
        userImages.setIdUser(userBd.getId());
        userImages.setUrlImage(urlPhoto);
        userBd.setProfilePictureUrl(urlPhoto);
        UserImages userImagedBd = userImagesRepository.save(userImages);
        if (userImagedBd == null) return null;
        return userRepository.save(userBd);
    }

    @Override
    public List<UserImages> getPhotosByIdUser(String idUser) {
        return userImagesRepository.findAllByIdUser(idUser);
    }
}
