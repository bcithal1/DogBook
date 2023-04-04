package dogbook.repository;

import dogbook.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    List<Event> findByHostId(Integer id);

}
