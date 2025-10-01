package com.konekt.backend.konket_backend.Controllers;

import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.DTO.ReactionRequestDTO;
import com.konekt.backend.konket_backend.Entities.Reaction;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.konket_backend.Services.ReactionService.IReactionService;
import com.konekt.backend.konket_backend.Services.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reaction")
public class ReactionController {

    @Autowired
    private IReactionService iReactionService;

    @Autowired
    private IUserService iUserService;


    @PostMapping
    public ResponseEntity<?> addUpdateReaction(@RequestBody ReactionRequestDTO reaction, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        Reaction reactionBd = iReactionService.addOrUpdateReactionPost(user.getId(), reaction);
        if (reactionBd == null){
            message.setMessage("Error al reaccionar");
            message.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
        }
        return ResponseEntity.ok(reactionBd);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addUpdateReactionComment(@RequestBody ReactionRequestDTO reaction, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        Reaction reactionBd = iReactionService.addOrUpdateReactionComment(user.getId(), reaction);
        if (reactionBd == null){
            message.setMessage("Error al reaccionar");
            message.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
        }
        return ResponseEntity.ok(reactionBd);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllReactionsPost(@PathVariable String postId){
        return ResponseEntity.ok(iReactionService.getAllReactionByPostId(postId));
    }
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getAllReactionsComment(@PathVariable String commentId){
        return ResponseEntity.ok(iReactionService.getAllReactionByCommentId(commentId));
    }
}
