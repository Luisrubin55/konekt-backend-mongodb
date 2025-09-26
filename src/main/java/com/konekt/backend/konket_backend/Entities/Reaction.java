package com.konekt.backend.konket_backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reactions")
public class Reaction {
    @Id
    private String id;
    private String userId;
    private String postId;
    private String commentId;
    private ReactionType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
