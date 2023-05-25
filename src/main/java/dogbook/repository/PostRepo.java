package dogbook.repository;

import dogbook.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepo extends JpaRepository <Post, Integer> {
    Optional<List<Post>> findByAuthorId(Integer authorId);

    Optional<Post> findByPostId (Integer postId);

    Optional<List<Post>> findByCommentId(Integer commentId);

    @Query("SELECT p FROM Post p WHERE :userId IN (SELECT tu FROM p.taggedUserId tu)")
    List<Post> findPostsByTaggedUserId(@Param("userId") Integer userId);

    @Query("SELECT p FROM Post p WHERE :dogId IN (SELECT tu FROM p.taggedDogId tu)")
    List<Post> findPostsByTaggedDogId(@Param("dogId") Integer dogId);

}
