package dogbook.repository;

import dogbook.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ChallengeRepo extends JpaRepository<Challenge, Integer> {

    Set<Challenge> findByNameContaining(String name);


    List<Challenge> findByEventId(Integer eventId);
}
