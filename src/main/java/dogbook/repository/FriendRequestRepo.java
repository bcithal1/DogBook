package dogbook.repository;

import dogbook.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepo extends JpaRepository<FriendRequest, Integer> {

    List<FriendRequest> findBySenderId(Integer senderId);
    List<FriendRequest> findByReceiverId(Integer receiverId);
}

