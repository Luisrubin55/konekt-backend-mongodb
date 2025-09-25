package com.konekt.backend.konket_backend.Entities.DTO;

import com.konekt.backend.konket_backend.Entities.User;

import java.time.LocalDateTime;

public class CommentWithUserDTO {
    private String id;
    private String content;
    private String idPost;
    private LocalDateTime createdAt;
    private UserResponseFeedDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserResponseFeedDTO getUser() {
        return user;
    }

    public void setUser(UserResponseFeedDTO user) {
        this.user = user;
    }
}
