package dogbook.repository;

import dogbook.model.FriendRequest;
import dogbook.model.UserLikedPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikedPostsRepo extends JpaRepository <UserLikedPosts, Integer> {

    List<UserLikedPosts> findByUserId(Integer userId);
}
