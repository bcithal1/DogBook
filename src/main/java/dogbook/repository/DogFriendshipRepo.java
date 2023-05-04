package dogbook.repository;

import dogbook.model.DogFriendship;
import dogbook.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogFriendshipRepo extends JpaRepository<DogFriendship, Integer> {
    List<DogFriendship> findByPrimaryUserId(Integer primaryUserId);
    List<DogFriendship> findBySecondaryUserId(Integer secondaryUserId);
}
