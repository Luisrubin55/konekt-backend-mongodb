package com.konekt.backend.konket_backend.Controllers;

import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.Friends.FriendResponseWithUserDTO;
import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.FriendRequest;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.konket_backend.Services.FriendsService.IFriendsService;
import com.konekt.backend.konket_backend.Services.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendsController {

    @Autowired
    private IFriendsService iFriendsService;

    @Autowired
    private IUserService iUserService;

    @PostMapping
    public ResponseEntity<?> sendUpdateRequestFriend(@RequestBody FriendRequestDTO friendRequest, @RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setSenderId(user.getId());
        friendRequestDTO.setReceiverId(friendRequest.getReceiverId());
        friendRequestDTO.setStatus(friendRequest.getStatus());
        FriendRequest friendDb = iFriendsService.sendRequest(friendRequestDTO);
        if (friendDb == null){
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            message.setMessage("Error al enviar la solicitud de amistad");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.setStatusCode(HttpStatus.OK.value());
        message.setMessage("Solicitud de amistad enviada");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping
    public ResponseEntity<?> getAllRequestFriend(@RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        List<FriendResponseWithUserDTO> friendRequestBd = iFriendsService.getAllRequestPendingByUserId(user.getId());
        if (friendRequestBd == null){
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            message.setMessage("No hay solicitudes de amistad");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(friendRequestBd);
    }
}