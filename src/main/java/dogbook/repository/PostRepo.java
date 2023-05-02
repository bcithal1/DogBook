package dogbook.repository;

import dogbook.model.FriendRequest;
import dogbook.model.Post;
import dogbook.model.UserLikedPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository <Post, Integer> {

}
