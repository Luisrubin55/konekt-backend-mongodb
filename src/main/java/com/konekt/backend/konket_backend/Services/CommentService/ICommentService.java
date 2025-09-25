package com.konekt.backend.konket_backend.Services.CommentService;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.CommentWithUserDTO;

import java.util.List;

public interface ICommentService {
    Comment addNewComment(String idPost, Comment comment);
    List<CommentWithUserDTO> getAllCommentsPost(String idPost);
    Comment updateComment(String idComment, Comment comment);
    Comment deleteComment(String idUser,String idComment);
}
