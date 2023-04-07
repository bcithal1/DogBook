package dogbook.repository;
import dogbook.model.DogTrick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface DogTrickRepo extends JpaRepository<DogTrick, Integer> {
        List<DogTrick> findByDogId(Integer dogId);

        //delete a trick
//        void delete(Integer trickId);
    }

