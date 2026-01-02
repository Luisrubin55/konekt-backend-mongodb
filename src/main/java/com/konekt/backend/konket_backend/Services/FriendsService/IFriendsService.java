package com.konekt.backend.konket_backend.Services.FriendsService;

import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendResponseWithUserDTO;
import com.konekt.backend.konket_backend.Entities.FriendRequest;

import java.util.List;

public interface IFriendsService {
    FriendRequest sendRequest(FriendRequestDTO requestDTO);
    List<FriendResponseWithUserDTO> getAllRequestPendingByUserId(String userId);
}
