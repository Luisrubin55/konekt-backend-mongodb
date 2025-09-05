package com.konekt.backend.konket_backend.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "reactions")
public class Reaction {
    @Id
    private Long id;
    private List<ReactionType> reactionTypes;
    private User user;
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ReactionType> getReactionTypes() {
        return reactionTypes;
    }

    public void setReactionTypes(List<ReactionType> reactionTypes) {
        this.reactionTypes = reactionTypes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
