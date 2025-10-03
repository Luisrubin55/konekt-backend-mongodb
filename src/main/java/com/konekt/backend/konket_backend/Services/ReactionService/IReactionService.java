package com.konekt.backend.konket_backend.Services.ReactionService;

import com.konekt.backend.konket_backend.Entities.DTO.ReactionRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.ReactionWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Reaction;

import java.util.List;

public interface IReactionService {
    Reaction addOrUpdateReactionPost(String userId, ReactionRequestDTO request);
    Reaction addOrUpdateReactionComment(String userId, ReactionRequestDTO request);
    List<ReactionWithUserDTO> getAllReactionByPostId(String postId);
    List<ReactionWithUserDTO> getAllReactionByCommentId(String commentId);
}
