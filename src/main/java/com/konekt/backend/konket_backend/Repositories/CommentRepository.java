package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByIdPost(String idPost);
}
