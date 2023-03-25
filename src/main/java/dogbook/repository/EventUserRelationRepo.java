package dogbook.repository;

import dogbook.model.EventUserRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventUserRelationRepo extends JpaRepository<EventUserRelations, Integer> {
}
