package com.konekt.backend.konket_backend.Entities.DTO;

import com.konekt.backend.konket_backend.Entities.ReactionType;

public class ReactionResponseDTO {
    private String id;
    private String userId;
    private ReactionType type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }
}
