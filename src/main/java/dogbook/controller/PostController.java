package dogbook.controller;

import dogbook.model.*;
import dogbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity createPost(@RequestBody Post post){
        Post response = postService.createPost(post);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> response  = postService.getPosts();
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/posts/user/{userId}")
    public ResponseEntity<List<Post>> getAllPostsByUserId(@PathVariable Integer userId) {
        List<Post> response = postService.getPostByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/posts/likes")
    public ResponseEntity<List<UserLikedPosts>> getLikesByUserId() {
        return postService.getLikesByUserID();
    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer postId){
        Post response = postService.getPostById(postId);
        return response==null? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Post> updatePostById(@PathVariable Integer postId, @RequestBody Post post ) {
        Post response = postService.updatePostById(postId, post);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/deletePost/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Integer postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/v1/addLike/{postId}")
    public ResponseEntity<Void> addLike(@PathVariable Integer postId) {
        postService.addLike(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/v1/deleteLike/{postId}")
    public ResponseEntity<Void> removeLike(@PathVariable Integer postId) {
        postService.removeLike(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/v1/addComment/{postId}")
    public ResponseEntity<Post> addComment(@PathVariable Integer postId, @RequestBody Post post) {
        Post response = postService.addComment(postId, post);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }
}


