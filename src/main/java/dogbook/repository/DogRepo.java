package dogbook.repository;

import dogbook.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepo extends JpaRepository<Dog,Integer> {
    @Query("SELECT d FROM Dog d WHERE d.id IN (SELECT do.dogId FROM DogOwner do WHERE do.userId = :ownerId)")
    List<Dog> findAllByOwnerId(@Param("ownerId") Integer ownerId);
}
