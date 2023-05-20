package dogbook.repository;

import dogbook.model.UserChallengeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChallengeRelationRepo extends JpaRepository<UserChallengeRelation, Integer> {
    List<UserChallengeRelation> findByUserId(Integer userId);
}
