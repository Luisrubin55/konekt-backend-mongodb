package com.konekt.backend.konket_backend.Services.PostService;

import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;

import java.util.List;

public interface IPostService {
    Post creaateNewPost(Post post);
    Post createPostWithImage(Post post,String urlImage);
    List<PostWithUserDTO> getAllPostsByUser(String idUser);
    Post updatePost(String idPost, Post post, String urlImage);
    Post deletePost(String postId, String userId);
    List<PostWithUserDTO> randomPost();
}
