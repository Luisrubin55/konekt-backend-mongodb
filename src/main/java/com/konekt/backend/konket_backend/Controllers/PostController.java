package com.konekt.backend.konket_backend.Controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.konekt.backend.konket_backend.Entities.DTO.MessageDTO;
import com.konekt.backend.konket_backend.Entities.DTO.PostRequestDTO;
import com.konekt.backend.konket_backend.Entities.DTO.PostWithUserDTO;
import com.konekt.backend.konket_backend.Entities.Post;
import com.konekt.backend.konket_backend.Entities.User;
import com.konekt.backend.konket_backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.konket_backend.Services.PostService.IPostService;
import com.konekt.backend.konket_backend.Services.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private IPostService iPostService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping
    public ResponseEntity<?> AddNewPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader){
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        post.setUserId(user.getId());
        Post postSaved = iPostService.creaateNewPost(post);
        MessageDTO message = new MessageDTO();
        if(postSaved == null){
            message.setMessage("Error al crear el post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postSaved);
    }

    @PostMapping("/createPostImage")
    public ResponseEntity<?> createPostWithImage(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart("content") PostRequestDTO postRequest, @RequestHeader("Authorization") String authHeader) throws IOException {
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User userBd = iUserService.getUserByEmail(email).orElseThrow();
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setUserId(userBd.getId());
        if (file == null || file.isEmpty()){
            Post postSaved = iPostService.creaateNewPost(post);
            if (postSaved == null) return  ResponseEntity.badRequest().build();
            return ResponseEntity.status(HttpStatus.CREATED).body(postSaved);
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "konekt"));
        Post postBd = iPostService.createPostWithImage(post, uploadResult.get("secure_url").toString());
        if(postBd == null){
            message.setMessage("Error al crear el post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postBd);
    }


    @GetMapping
    public ResponseEntity<?> GetAllPosByUser(@RequestHeader("Authorization") String authHeader){
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        List<PostWithUserDTO> getAllPost = iPostService.getAllPostsByUser(user.getId());
        if (getAllPost == null){
            message.setMessage("Error al crear el post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(getAllPost);
    }

    @GetMapping("/random-post")
    public ResponseEntity<?> RandomPost() {
        MessageDTO message = new MessageDTO();
        List<PostWithUserDTO> getRandomPost = iPostService.randomPost();
        if (getRandomPost == null){
            message.setMessage("Error al recuperar los post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(getRandomPost);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> UpdatePost(@RequestPart(value = "file", required = false) MultipartFile file, @PathVariable String id, @RequestBody Post post, @RequestHeader("Authorization") String authHeader) throws IOException {
        MessageDTO message = new MessageDTO();
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        post.setUserId(user.getId());


        if (file == null || file.isEmpty()){
            Post postSaved = iPostService.updatePost(post);
            if (postSaved == null) return  ResponseEntity.badRequest().build();
            return ResponseEntity.status(HttpStatus.CREATED).body(postSaved);
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "konekt"));
        Post postUpdated = iPostService.updatePost(id,post,uploadResult.get("secure_url").toString());

        if (postUpdated == null){
            message.setMessage("Error al actualizar el post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeletePost(@PathVariable String id, @RequestHeader("Authorization") String authHeader){
        String email = DecodedJWTValidation.decodedJWT(authHeader);
        User user = iUserService.getUserByEmail(email).orElseThrow();
        Post postDeleted = iPostService.deletePost(id, user.getId());
        MessageDTO message = new MessageDTO();
        if (postDeleted == null){
            message.setMessage("Error al eliminar el post");
            message.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.setMessage("Post eliminado");
        message.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
