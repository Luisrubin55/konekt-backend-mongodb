package com.konekt.backend.konket_backend.Services.PostService;

import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import com.konekt.backend.konket_backend.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService{

    @Autowired
    PostRepository postRepository;

    @Override
    @Transactional
    public Post creaateNewPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<PostWithUserDTO> getAllPostsByUser(String idUser) {
        return postRepository.findPostsByUserId(idUser);
    }


    @Override
    public Post updatePost(String idPost, Post post) {
        Optional<Post> postBd = postRepository.findById(idPost);
        if (postBd.isEmpty()) return null;
        Post postUpdated = postBd.orElseThrow();
        postUpdated.setContent(post.getContent());
        postUpdated.setComments(post.getComments());
        postUpdated.setLikes(post.getLikes());
        postUpdated.setUrlImage(post.getUrlImage());
        return postRepository.save(postUpdated);
    }

    @Override
    public Post deletePost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) return null;
        postRepository.delete(post.orElseThrow());
        return post.orElseThrow();
    }

    @Override
    public List<PostWithUserDTO> randomPost() {
        return postRepository.findAllWithUsers(15);
    }

}
