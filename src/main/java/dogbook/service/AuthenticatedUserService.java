package dogbook.service;

import dogbook.enums.AccessLevel;
import dogbook.model.DogOwner;
import dogbook.repository.DogOwnerRepo;
import dogbook.repository.DogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticatedUserService {

    @Autowired
    DogOwnerService dogOwnerService;



    public boolean hasId(Integer id){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(role -> role.getAuthority().equals("USER_" + id)).count() > 0;
    }

    public Integer getId(){
        var userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(role -> role.getAuthority().startsWith("USER_")).findFirst().orElse(null);
        if(userRole == null){
            return null;
        }

        return Integer.parseInt(userRole.getAuthority().replace("USER_", ""));
    }

    public boolean validateDogOwnership(Integer dogId) {
        List<DogOwner> validUsers = dogOwnerService.getAllByDogId(dogId);
        for (DogOwner owner : validUsers) {
            if (owner.getUserId().equals(getId()) &&
                    (owner.getAccessLevel() == AccessLevel.PRIMARY_OWNER
                            || owner.getAccessLevel() == AccessLevel.SECONDARY_OWNER)){
                return true;
            }
        }
        return false;
    }
}
