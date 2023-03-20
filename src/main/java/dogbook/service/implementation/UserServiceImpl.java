package dogbook.service.implementation;

import dogbook.model.User;
import dogbook.repository.UserRepo;
import dogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public Optional<User> getUserById(Integer id){
        return userRepo.findById(id);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    @Override
    public User getUserByProviderAccount(String name, String accountId){
        return userRepo.findByProviderAccount(name, accountId);
    }

    @Override
    public User createUser(User user){

        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user){ return userRepo.save(user);}

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
