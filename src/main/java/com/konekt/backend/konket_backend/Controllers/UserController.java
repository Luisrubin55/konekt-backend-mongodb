package com.konekt.backend.konket_backend.Controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.konket_backend.Services.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public ResponseEntity<?> GetUserByEmail(@RequestHeader("Authorization") String authHeader) {
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User userBd = iUserService.getUserByEmail(email).orElseThrow();
        if (userBd == null){
            message.setMessage("Error al recuperar el usuario");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userBd);
    }

    @PatchMapping(value = "/update-photo-profile", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> UpdateUserPhotoProfile(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authHeader) throws IOException {
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User userBd = iUserService.getUserByEmail(email).orElseThrow();
        if (userBd == null){
            message.setMessage("Error al recuperar el usuario");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (file.isEmpty()) {
            message.setMessage("La imagen es obligatoria");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "konekt"));
        User userUpdated = iUserService.updatePhotoProfile(userBd.getId(), uploadResult.get("secure_url").toString());
        if (userUpdated == null){
            message.setMessage("Upps Algo salio mal");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.setMessage("Perfil actualizado");
        message.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


}
