package com.konekt.backend.konket_backend.Services.PostService;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import com.konekt.backend.konket_backend.Entities.UserImages;
import com.konekt.backend.konket_backend.Repositories.CommentRepository;
import com.konekt.backend.konket_backend.Repositories.PostRepository;
import com.konekt.backend.konket_backend.Repositories.UserImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserImagesRepository userImagesRepository;


    @Override
    @Transactional
    public Post creaateNewPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post createPostWithImage(Post post, String urlImage) {
        UserImages userImages = new UserImages();
        userImages.setUrlImage(urlImage);
        userImages.setIdUser(post.getUserId());
        UserImages imagesBd = userImagesRepository.save(userImages);
        List<String> idImage = new ArrayList<>();
        idImage.add(imagesBd.getId());
        post.setIdImages(idImage);
        return postRepository.save(post);
    }

    @Override
    public List<PostWithUserDTO> getAllPostsByUser(String idUser) {
        return postRepository.findPostsByUserId(idUser);
    }


    @Override
    public Post updatePost(String idPost, Post post, String urlImage) {
        Optional<Post> postBd = postRepository.findById(idPost);
        if (postBd.isEmpty()) return null;
        Post postUpdated = postBd.orElseThrow();
        if (!postUpdated.getUserId().equals(post.getUserId())) return null;
        postUpdated.setContent(post.getContent());
        postUpdated.setComments(post.getComments());
        postUpdated.setLikes(post.getLikes());
        return postRepository.save(postUpdated);
    }

    @Override
    public Post deletePost(String postId, String userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) return null;
        Post postBd = optionalPost.orElseThrow();
        if (!postBd.getUserId().equals(userId)) return null;
        postRepository.delete(postBd);
        List<Comment> comments = commentRepository.findByIdPost(postBd.getId());
        if (!comments.isEmpty()) comments.forEach(comment -> commentRepository.delete(comment));
        return postBd;
    }

    @Override
    public List<PostWithUserDTO> randomPost() {
        return postRepository.findAllWithUsers(15);
    }

}
