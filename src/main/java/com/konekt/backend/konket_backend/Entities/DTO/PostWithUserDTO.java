package com.konekt.backend.konket_backend.Entities.DTO;

import com.konekt.backend.konket_backend.Entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostWithUserDTO {
    private String id;
    private String content;
    private String urlImage;
    private LocalDateTime createdAt;
    private List<String> comments = new ArrayList<>();
    private List<String> likes = new ArrayList<>();
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public UserResponseFeedDTO getUser() {
        return user;
    }

    public void setUser(UserResponseFeedDTO user) {
        this.user = user;
    }
}
