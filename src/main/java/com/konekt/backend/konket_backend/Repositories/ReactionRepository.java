package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.DTO.ReactionWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Reaction;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, String> {
    Optional<Reaction> findByUserIdAndPostId(String userId, String postId);
    Optional<Reaction> findByUserIdAndCommentId(String userId, String commentId);

    @Aggregation(pipeline = {
            "{ $match: { postId: ?0 } }",
            "{ $addFields: { userIdObj: { $toObjectId: '$userId' } } }",
            "{ $lookup: { from: 'users', localField: 'userIdObj', foreignField: '_id', as: 'user' } }",
            "{ $unwind: '$user' }"
    })
    List<ReactionWithUserDTO> findByPostId(String postId);

    @Aggregation(pipeline = {
            "{ $match: { commentId: ?0 } }",
            "{ $addFields: { userIdObj: { $toObjectId: '$userId' } } }",
            "{ $lookup: { from: 'users', localField: 'userIdObj', foreignField: '_id', as: 'user' } }",
            "{ $unwind: '$user' }"
    })
    List<ReactionWithUserDTO> findByCommentId(String commentId);
}
