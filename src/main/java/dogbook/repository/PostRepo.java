package dogbook.repository;

import dogbook.model.FriendRequest;
import dogbook.model.Post;
import dogbook.model.UserLikedPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository <Post, Integer> {
    Optional<List<Post>> findByAuthorId(Integer authorId);

    Optional<Post> findByPostId (Integer postId);


}
