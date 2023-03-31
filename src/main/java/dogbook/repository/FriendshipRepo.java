package dogbook.repository;

import dogbook.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship, Integer> {

    List<Friendship> findByPrimaryUserId(Integer primaryUserId);
    List<Friendship> findBySecondaryUserId(Integer secondaryUserId);

}
