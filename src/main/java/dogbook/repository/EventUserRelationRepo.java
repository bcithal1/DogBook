package dogbook.repository;

import dogbook.model.EventUserRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventUserRelationRepo extends JpaRepository<EventUserRelations, Integer> {


    List<EventUserRelations> findByUserId(Integer id);
    List<EventUserRelations> findByEventId(Integer id);


}
