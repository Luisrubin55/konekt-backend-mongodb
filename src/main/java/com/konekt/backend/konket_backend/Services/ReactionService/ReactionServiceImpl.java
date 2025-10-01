package com.konekt.backend.konket_backend.Services.ReactionService;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.ReactionRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.ReactionWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import com.konekt.backend.konket_backend.Entities.Reaction;
import com.konekt.backend.konket_backend.Repositories.CommentRepository;
import com.konekt.backend.konket_backend.Repositories.PostRepository;
import com.konekt.backend.konket_backend.Repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReactionServiceImpl implements IReactionService{

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public Reaction addOrUpdateReactionPost(String userId, ReactionRequestDTO request) {
        Optional<Reaction> reactionExistPost = reactionRepository.findByUserIdAndPostId(userId, request.getPostId());
        Optional<Post> optionalPost = postRepository.findById(request.getPostId());
        if (optionalPost.isEmpty()) return null;
        Post postBd = optionalPost.orElseThrow();
        if (reactionExistPost.isPresent()){
            Reaction reactionPost = reactionExistPost.orElseThrow();
            if (reactionPost.getType() == request.getType()){
                reactionRepository.delete(reactionPost);
                List<String> reactionsPost = postBd.getLikes().stream().filter(item -> !Objects.equals(item, reactionPost.getId())).toList();
                postBd.setLikes(reactionsPost);
                postRepository.save(postBd);
                return null;
            }
            reactionPost.setType(request.getType());
            return reactionRepository.save(reactionPost);
        }
        Reaction reaction = new Reaction();
        reaction.setUserId(userId);
        reaction.setType(request.getType());
        reaction.setCommentId(request.getCommentId());
        reaction.setPostId(request.getPostId());
        Reaction reactionBd = reactionRepository.save(reaction);
        List<String> reactionsPost = postBd.getLikes();
        reactionsPost.add(reactionBd.getId());
        postBd.setLikes(reactionsPost);
        postRepository.save(postBd);
        return reactionBd;
    }

    @Override
    public Reaction addOrUpdateReactionComment(String userId, ReactionRequestDTO request) {
        Optional<Reaction> reactionExistComment = reactionRepository.findByUserIdAndCommentId(userId, request.getCommentId());
        Optional<Comment> commentOptional = commentRepository.findById(request.getCommentId());
        if (commentOptional.isEmpty()) return null;
        Comment commentBd = commentOptional.orElseThrow();
        if (reactionExistComment.isPresent()){
            Reaction reactionComment = reactionExistComment.orElseThrow();
            if (reactionComment.getType() == request.getType()){
                reactionRepository.delete(reactionComment);
                List<String> reactionsComment = commentBd.getLikes().stream().filter(item -> !Objects.equals(item, reactionComment.getId())).toList();
                commentBd.setLikes(reactionsComment);
                commentRepository.save(commentBd);
                return null;
            }
            reactionComment.setType(request.getType());
            return reactionRepository.save(reactionComment);
        }
        Reaction reaction = new Reaction();
        reaction.setUserId(userId);
        reaction.setType(request.getType());
        reaction.setCommentId(request.getCommentId());
        reaction.setPostId(request.getPostId());
        Reaction reactionBd = reactionRepository.save(reaction);
        List<String> reactionsComment = commentBd.getLikes();
        reactionsComment.add(reactionBd.getId());
        commentBd.setLikes(reactionsComment);
        commentRepository.save(commentBd);
        return reactionBd;
    }

    @Override
    public List<ReactionWithUserDTO> getAllReactionByPostId(String postId) {
         return reactionRepository.findByPostId(postId);
    }

    @Override
    public List<ReactionWithUserDTO> getAllReactionByCommentId(String commentId) {
        return reactionRepository.findByCommentId(commentId);
    }
}
