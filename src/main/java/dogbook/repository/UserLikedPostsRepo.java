package dogbook.repository;

import dogbook.model.FriendRequest;
import dogbook.model.Post;
import dogbook.model.User;
import dogbook.model.UserLikedPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikedPostsRepo extends JpaRepository <UserLikedPosts, Integer> {

    List<UserLikedPosts> findByUserId(Integer userId);
    Optional<UserLikedPosts> findByPostId (Integer postId);

}
