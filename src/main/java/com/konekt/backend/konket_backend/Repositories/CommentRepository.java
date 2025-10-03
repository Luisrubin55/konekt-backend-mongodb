package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.CommentWithUserDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Aggregation(pipeline = {
            "{ $match: { idPost: ?0 } }",
            "{ $addFields: { userIdObj: { $toObjectId: '$idUser' } } }",
            "{ $lookup: { from: 'users', localField: 'userIdObj', foreignField: '_id', as: 'user' } }",
            "{ $addFields: { likesObj: { $map: { input: '$likes', as: 'likeId', in: { $toObjectId: '$$likeId' } } } } }",
            "{ $lookup: { from: 'reactions', localField: 'likesObj', foreignField: '_id', as: 'likes' } }",
            "{ $unwind: '$user' }",
            "{ $sort: { createdAt: -1 } }"
    })
    List<CommentWithUserDTO> findCommentsByPostId(String postId);

    List<Comment> findByIdPost(String id);
}
