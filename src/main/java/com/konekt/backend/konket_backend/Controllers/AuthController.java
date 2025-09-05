package com.konekt.backend.konket_backend.Controllers;

import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.DTO.UserRequestDTO;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Services.Authentication.IAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthenticateService iAuthenticateService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO user){
        User userBD = iAuthenticateService.AddNewUser(user);
        MessageDTO message = new MessageDTO();
        if(userBD == null){
            message.setMessage("Error al crear el usaurio");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.setMessage("Cuenta creada revisa tu email");
        message.setStatusCode(HttpStatus.CREATED.value());
        return  ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/validate-account")
    public ResponseEntity<?> validateTokenAccount(@RequestBody Map<String, String> requestToken){
        MessageDTO message = new MessageDTO();
        Boolean validatedToken = iAuthenticateService.validateToken(requestToken.get("token"));
        if (validatedToken.equals(false)){
            message.setMessage("Error al confirmar la cuenta");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.setMessage("Cuenta confirmada, Puedes iniciar sesión");
        message.setStatusCode(HttpStatus.CREATED.value());
        return  ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/search-account")
    public ResponseEntity<?> recoverPasswordToken(@RequestBody Map<String, String> email){
        Optional<User> userBD = iAuthenticateService.recoverPasswordToken(email.get("email"));
        MessageDTO message = new MessageDTO();
        if (userBD.isEmpty()) {
            message.setMessage("Email no pertenece a alguna cuenta registrada");
            message.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.setStatusCode(HttpStatus.OK.value());
        message.setMessage("Token enviado, revisa tu correo electronico");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
    }

    @PostMapping("/new-password/{token}")
    public ResponseEntity<?> recoverPassword(@PathVariable String token, @RequestBody Map<String, String> newPassword) {
        Optional<User> userOptional = iAuthenticateService.recoverPassword(token, newPassword.get("password"));
        MessageDTO message = new MessageDTO();
        if (userOptional.isPresent()) {
            message.setMessage("Contraseña restablecida puedes iniciar sesión");
            message.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        message.setMessage("Error al intentar cambiar la contraseña.");
        message.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
