package com.konekt.backend.konket_backend.Services.FriendsService;

import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendResponseWithUserDTO;
import com.konekt.backend.konket_backend.Entities.FriendRequest;
import com.konekt.backend.konket_backend.Repositories.FriendRequestRepository;
import com.konekt.backend.konket_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements IFriendsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Override
    public FriendRequest sendRequest(FriendRequestDTO friendRequest) {
        if (Objects.equals(friendRequest.getSenderId(), friendRequest.getReceiverId())) return null;
        Optional<FriendRequest> friendBd =  friendRequestRepository.findBySenderIdAndReceiverId(friendRequest.getSenderId(), friendRequest.getReceiverId());
        if (friendBd.isPresent()){
            friendRequestRepository.delete(friendBd.orElseThrow());
            return null;
        }
        FriendRequest newFriendRequest = new FriendRequest();
        newFriendRequest.setSenderId(friendRequest.getSenderId());
        newFriendRequest.setReceiverId(friendRequest.getReceiverId());
        newFriendRequest.setStatus(friendRequest.getStatus());
        return friendRequestRepository.save(newFriendRequest);
    }

    @Override
    public List<FriendResponseWithUserDTO> getAllRequestPendingByUserId(String userId) {
        List<FriendResponseWithUserDTO> requestList = friendRequestRepository.findByReceiverId(userId);
        List<FriendResponseWithUserDTO> users = requestList.stream().filter(req -> !req.getStatus().equals(FriendRequest.Status.REJECTED) || !req.getStatus().equals(FriendRequest.Status.ACCEPTED)).toList();
        return users;
    }
}
