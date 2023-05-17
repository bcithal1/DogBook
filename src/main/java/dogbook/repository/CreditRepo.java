package dogbook.repository;

import dogbook.model.Challenge;
import dogbook.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepo extends JpaRepository<Credit, Integer> {

}
