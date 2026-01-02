package com.konekt.backend.konket_backend.Repositories;

import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendResponseWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Friend;
import com.konekt.backend.konket_backend.Entities.FriendRequest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {

    Optional<FriendRequest> findBySenderIdAndReceiverId(String senderId, String receiverId);

    @Aggregation(pipeline = {
            "{ $match: { receiverId: ?0 } }",
            "{ $addFields: { senderObjId: { $toObjectId: '$senderId' } } }",
            "{ $lookup: { from: 'users', localField: 'senderObjId', foreignField: '_id', as: 'sender' } }",
            "{ $unwind: '$sender' }",
            "{ $project: { " +
                    "id: '$_id', " +
                    "sentAt: 1, " +
                    "status: 1, " +
                    "user: '$sender' " +
                    "} }"
    })
    List<FriendResponseWithUserDTO> findByReceiverId(String receiverId);
}
