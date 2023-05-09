package dogbook.repository;

import dogbook.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepo extends CrudRepository<UserProfile, Integer> {

}
