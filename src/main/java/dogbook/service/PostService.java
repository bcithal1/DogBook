package dogbook.service;

import dogbook.model.*;
import dogbook.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    PostRepo postRepo;

    @Autowired
    UserLikedPostsRepo userLikedPostsRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    public Post createPost(Post post) {
        post.setDateTime(new Date());
        post.setAuthorId(authenticatedUserService.getId());
        return postRepo.save(post);
    }

    public Post getPostById(Integer postId) {
        Post post = null;
        if (postRepo.findById(postId).isPresent())
            post = postRepo.findById(postId).get();

        return post;
    }

    public List<Post> getPostByUserId(Integer userId) {

        if (postRepo.findByAuthorId(userId).isPresent()) {
            return postRepo.findByAuthorId(userId).get();
        } else {throw new HttpClientErrorException(HttpStatus.NOT_FOUND);}
    }

    public Post updatePostById(Integer postId, Post post) {
        Optional<Post> postFound = postRepo.findById(postId);
        Integer currentUser = authenticatedUserService.getId();

        if (postFound.isPresent()) {
            if (postFound.get().getAuthorId().equals(currentUser)) {
                postFound.get().setMessage(post.getMessage());
                return postRepo.save(postFound.get());
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    public void deletePostById(Integer postId) {
        Optional<Post> currentPost = postRepo.findById(postId);
        Integer currentUser = authenticatedUserService.getId();

        if (currentPost.isPresent()) {
            if (currentPost.get().getAuthorId().equals(currentUser)) {
                postRepo.deleteById(postId);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<UserLikedPosts>> getLikesByUserID(){
        Integer currentUser = authenticatedUserService.getId();
        return ResponseEntity.ok(userLikedPostsRepo.findByUserId(currentUser));
    }

    public void addLike(Integer postId) {
        Optional<Post> currentPost = postRepo.findById(postId);
        Integer currentUser = authenticatedUserService.getId();

        if (currentPost.get().getAuthorId().equals(currentUser)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        List<UserLikedPosts> userLikedPosts = userLikedPostsRepo.findByUserId(currentUser);

        for (UserLikedPosts post : userLikedPosts) {
            if (post.getPostId().equals(postId)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<Post> post = postRepo.findById(postId);
        post.get().setLikeCount(post.get().getLikeCount() == null ? 1 : post.get().getLikeCount() + 1);
        postRepo.save(post.get());

        userLikedPostsRepo.save(new UserLikedPosts(currentUser, postId));
    }

    public void removeLike(Integer postId) {
        Optional<Post> currentPost = postRepo.findById(postId);
        Integer currentUser = authenticatedUserService.getId();

        if (currentPost.get().getAuthorId().equals(currentUser)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        List<UserLikedPosts> userLikedPosts = userLikedPostsRepo.findByUserId(currentUser);

        for (UserLikedPosts post : userLikedPosts) {
            if (!post.getPostId().equals(postId)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        }
        Optional<Post> post = postRepo.findById(postId);
        post.get().setLikeCount(post.get().getLikeCount() == null ? null : post.get().getLikeCount() - 1);
        postRepo.save(post.get());

        userLikedPostsRepo.deleteById(postId);
    }

    public Post addComment(Integer postId, Post comment) {
        Optional<Post> post = postRepo.findById(postId);
        Integer currentUser = authenticatedUserService.getId();

        comment.setCommentId(postId);
        comment.setDateTime(new Date());
        postRepo.save(comment);

        post.get().setCommentCount(post.get().getCommentCount() == null ? 1 : post.get().getCommentCount() + 1);
        return postRepo.save(post.get());
    }
}
