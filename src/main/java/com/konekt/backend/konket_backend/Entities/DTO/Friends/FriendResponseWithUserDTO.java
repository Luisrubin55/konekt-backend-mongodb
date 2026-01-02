package com.konekt.backend.konket_backend.Entities.DTO.Friends;

import com.konekt.backend.konket_backend.Entities.DTO.UserResponseFeedDTO;
import com.konekt.backend.konket_backend.Entities.FriendRequest;

import java.time.LocalDateTime;

public class FriendResponseWithUserDTO {
    private String id;
    private UserResponseFeedDTO user;
    private LocalDateTime sentAt;
    private FriendRequest.Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserResponseFeedDTO getUser() {
        return user;
    }

    public void setUser(UserResponseFeedDTO user) {
        this.user = user;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public FriendRequest.Status getStatus() {
        return status;
    }

    public void setStatus(FriendRequest.Status status) {
        this.status = status;
    }
}
