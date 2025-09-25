package com.konekt.backend.konket_backend.Services.CommentService;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.CommentWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import com.konekt.backend.konket_backend.Repositories.CommentRepository;
import com.konekt.backend.konket_backend.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment addNewComment(String idPost, Comment comment) {
        Optional<Post> postBd = postRepository.findById(idPost);
        if(postBd.isEmpty()) return null;
        Post postUpdated = postBd.orElseThrow();
        comment.setIdPost(idPost);
        Comment commentSaved = commentRepository.save(comment);
        List<String> comments = postUpdated.getComments();
        comments.add(commentSaved.getId());
        postUpdated.setComments(comments);
        postRepository.save(postUpdated);
        return commentSaved;
    }

    @Override
    public List<CommentWithUserDTO> getAllCommentsPost(String idPost) {
       return commentRepository.findCommentsByPostId(idPost);
    }

    @Override
    public Comment updateComment(String idComment, Comment comment) {
        Optional<Comment> commentDd = commentRepository.findById(idComment);
        if (commentDd.isEmpty()) return  null;
        Comment commentUpdted = commentDd.orElseThrow();
        if (!commentUpdted.getIdUser().equals(comment.getIdUser())) return null;
        commentUpdted.setContent(comment.getContent());
        commentUpdted.setUrlImage(comment.getUrlImage());
        return commentRepository.save(commentUpdted);
    }

    @Override
    public Comment deleteComment(String idUser, String idComment) {
       Optional<Comment> optionalComment = commentRepository.findById(idComment);
       if (optionalComment.isEmpty()) return null;
       Comment commentBd = optionalComment.orElseThrow();
       if (!commentBd.getIdUser().equals(idUser)) return null;
       commentRepository.deleteById(idComment);
       Optional<Post> optionalPost = postRepository.findById(commentBd.getIdPost());
       if (optionalPost.isEmpty()) return  null;
       Post postBd = optionalPost.orElseThrow();
       List<String> commentsFilter = postBd.getComments().stream().filter(comment -> !comment.equals(commentBd.getId())).toList();
       postBd.setComments(commentsFilter);
       postRepository.save(postBd);
       return commentBd;
    }
}
