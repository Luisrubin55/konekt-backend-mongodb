package com.konekt.backend.konket_backend.Entities.DTO.Friends;

import com.konekt.backend.konket_backend.Entities.FriendRequest;

public class FriendRequestDTO {
    private String senderId;
    private String receiverId;
    private FriendRequest.Status status;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public FriendRequest.Status getStatus() {
        return status;
    }

    public void setStatus(FriendRequest.Status status) {
        this.status = status;
    }
}
