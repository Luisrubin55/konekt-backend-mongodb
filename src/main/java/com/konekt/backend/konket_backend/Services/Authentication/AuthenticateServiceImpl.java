package com.konekt.backend.konket_backend.Services.Authentication;

import com.konekt.backend.konket_backend.Entities.DTO.UserRequestDTO;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticateServiceImpl implements IAuthenticateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User AddNewUser(UserRequestDTO user) {
        User userBD = new User();
        Random random = new Random();
        String token = String.valueOf(System.currentTimeMillis()*random.nextInt(99999)).substring(0,6);
        userBD.setName(user.getName());
        userBD.setPaternalSurname(user.getPaternalSurname());
        userBD.setBirthdate(user.getBirthdate());
        userBD.setGenre(user.getGenre());
        userBD.setActive(false);
        userBD.setToken(token);
        userBD.setEmail(user.getEmail());
        userBD.setUsername("@"+user.getName().concat(".").concat(user.getPaternalSurname()).concat(String.valueOf(random.nextInt(999))));
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        userBD.setPassword(passwordEncoded);
        return userRepository.save(userBD);
    }

    @Override
    @Transactional
    public Boolean validateToken(String token) {
        Optional<User> userExist = userRepository.findByToken(token);
        if (userExist.isPresent()){
            User userBd = userExist.orElseThrow();
            if (!userBd.getToken().equals(token)) return false;
            userBd.setActive(true);
            userBd.setToken(null);
            userRepository.save(userBd);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> recoverPasswordToken(String email) {
        Random random = new Random();
        Optional<User> userOptional = userRepository.findByEmail(email);
        String token = String.valueOf(System.currentTimeMillis()*random.nextInt(99999)).substring(0,6);
        if (userOptional.isPresent()){
            User userBd = userOptional.orElseThrow();
            userBd.setToken(token);
            User userSave = userRepository.save(userBd);
            return Optional.of(userSave);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> recoverPassword(String token, String password) {
        Optional<User> userOptional = userRepository.findByToken(token);
        if (userOptional.isPresent()){
            User userBd = userOptional.orElseThrow();
            if (userBd.getToken().equals(token)){
                String passwordEncoded = passwordEncoder.encode(password);
                userBd.setPassword(passwordEncoded);
                userBd.setToken(null);
                return Optional.of(userRepository.save(userBd));
            }
        }
        return Optional.empty();
    }

}
