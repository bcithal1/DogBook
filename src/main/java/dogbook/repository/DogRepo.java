package dogbook.repository;

import dogbook.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepo extends JpaRepository<Dog,Integer> {
    List<Dog> findAllByUserId(Integer userId);
}
