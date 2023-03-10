package dogbook.repository;

import dogbook.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepo extends JpaRepository<Provider, Integer> {
    List<Provider> findAllByUserId(Integer userId);
}
