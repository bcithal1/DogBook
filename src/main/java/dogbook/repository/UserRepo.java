package dogbook.repository;

import dogbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("SELECT u FROM Users u WHERE u.id IN (SELECT p.userId FROM Provider p WHERE p.name = :name AND p.accountId = :accountId)")
    User findByProviderAccount(@Param("name") String name, @Param("accountId") String accountId);
}
