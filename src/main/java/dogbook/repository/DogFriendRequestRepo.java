package dogbook.repository;

import dogbook.model.DogFriendRequest;
import dogbook.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogFriendRequestRepo extends JpaRepository<DogFriendRequest, Integer> {

    List<DogFriendRequest> findBySenderId(Integer senderId);
    List<DogFriendRequest> findByReceiverId(Integer receiverId);
}
