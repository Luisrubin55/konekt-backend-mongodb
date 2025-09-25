package com.konekt.backend.konket_backend.Controllers;

import com.konekt.backend.konket_backend.Entities.Comment;
import com.konekt.backend.konket_backend.Entities.DTO.CommentWithUserDTO;
import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.konket_backend.Services.CommentService.ICommentService;
import com.konekt.backend.konket_backend.Services.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IUserService iUserService;

    @PostMapping("/{idPost}")
    public ResponseEntity<?> addComment(@PathVariable String idPost, @RequestBody Comment comment, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        comment.setIdUser(user.getId());
        Comment commentBd = iCommentService.addNewComment(idPost, comment);
        if (commentBd == null){
            message.setMessage("Error al crear el comentario");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentBd);
    }


    @GetMapping("/{idPost}")
    public ResponseEntity<?> getCommentsByIdPost(@PathVariable String idPost){
        MessageDTO message = new MessageDTO();
        List<CommentWithUserDTO> commentsBd = iCommentService.getAllCommentsPost(idPost);
        if (commentsBd == null){
            message.setMessage("Aún no hay comentarios.");
            message.setStatusCode(HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentsBd);
    }

    @PatchMapping("/{idComment}")
    public ResponseEntity<?> updateCommentById(@PathVariable String idComment, @RequestBody Comment comment, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        comment.setIdUser(user.getId());
        Comment commentBd = iCommentService.updateComment(idComment,comment);
        if (commentBd == null){
            message.setMessage("Error al actualizar el comentario");
            message.setStatusCode(HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentBd);
    }

    @DeleteMapping("/{idComment}")
    public ResponseEntity<?> updateCommentById(@PathVariable String idComment, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        if (user == null) return ResponseEntity.notFound().build();
        Comment comentDeleted = iCommentService.deleteComment(user.getId(),idComment);
        if (comentDeleted == null){
            message.setMessage("Error al eliminar el comentario");
            message.setStatusCode(HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(comentDeleted);
    }

}
