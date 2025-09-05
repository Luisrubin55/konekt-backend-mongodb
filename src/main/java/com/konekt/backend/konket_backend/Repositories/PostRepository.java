package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Aggregation(pipeline = {
            "{ $match: { userId: ?0 } }",
            "{ $addFields: { userIdObj: { $toObjectId: '$userId' } } }",
            "{ $lookup: { from: 'users', localField: 'userIdObj', foreignField: '_id', as: 'user' } }",
            "{ $unwind: '$user' }"
    })
    List<PostWithUserDTO> findPostsByUserId(String userId);

    @Aggregation(pipeline = {
            "{ $addFields: { userIdObj: { $toObjectId: '$userId' } } }",
            "{ $lookup: { from: 'users', localField: 'userIdObj', foreignField: '_id', as: 'user' } }",
            "{ $unwind: '$user' }",
            "{ $sample: { size: ?0 } }"
    })
    List<PostWithUserDTO> findAllWithUsers(int size);
}
