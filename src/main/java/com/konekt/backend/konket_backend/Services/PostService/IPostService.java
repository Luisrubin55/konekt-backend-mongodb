package com.konekt.backend.konket_backend.Services.PostService;

import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;

import java.util.List;

public interface IPostService {
    Post creaateNewPost(Post post);
    List<PostWithUserDTO> getAllPostsByUser(String idUser);
    Post updatePost(String idPost, Post post);
    Post deletePost(String postId);
    List<PostWithUserDTO> randomPost();
}
