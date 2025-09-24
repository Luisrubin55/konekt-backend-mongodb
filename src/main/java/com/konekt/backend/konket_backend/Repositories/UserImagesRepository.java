package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.UserImages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImagesRepository extends MongoRepository<UserImages, String> {
    List<UserImages> findAllByIdUser(String idUser);
}
