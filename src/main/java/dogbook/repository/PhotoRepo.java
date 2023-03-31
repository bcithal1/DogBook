package dogbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dogbook.model.Photo;

@Repository
public interface PhotoRepo extends JpaRepository<Photo,Integer> {
}