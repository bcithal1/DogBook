package dogbook.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticatedUserService {

    public boolean hasId(Integer id){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(role -> role.getAuthority().equals("USER_" + id)).count() > 0;
    }

    public Integer getId(){
        var userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(role -> role.getAuthority().startsWith("USER_")).findFirst().orElse(null);
        if(userRole == null){
            return null;
        }

        Integer userId = Integer.parseInt(userRole.getAuthority().replace("USER_", ""));
        return userId;
    }
}
