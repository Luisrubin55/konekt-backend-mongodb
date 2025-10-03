package com.konekt.backend.konket_backend.Entities.DTO;

import com.konekt.backend.konket_backend.Entities.ReactionType;

public class ReactionWithUserDTO {
    private String id;
    private UserResponseFeedDTO user;
    private ReactionType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserResponseFeedDTO getUser() {
        return user;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }
}
