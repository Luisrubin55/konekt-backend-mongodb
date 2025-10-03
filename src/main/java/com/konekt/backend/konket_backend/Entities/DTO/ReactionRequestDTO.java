package com.konekt.backend.konket_backend.Entities.DTO;

import com.konekt.backend.konket_backend.Entities.ReactionType;

public class ReactionRequestDTO {
    private String postId;
    private String commentId;
    private ReactionType type;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }
}
