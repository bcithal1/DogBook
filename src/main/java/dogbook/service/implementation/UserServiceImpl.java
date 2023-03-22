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
    public User updateUser(Integer id, User user){

        Optional<User> userFound = userRepo.findById(id);
        if(userFound.isPresent()){
            userFound.get().setDisplayName(user.getDisplayName());
            userFound.get().setFullName(user.getFullName());
            userFound.get().setProfilePhotoUrl(user.getProfilePhotoUrl());
            userFound.get().setAddress(user.getAddress());
            userFound.get().setEmail(user.getEmail());
            userFound.get().setGender(user.getGender());
            userFound.get().setDate_of_birth(user.getDate_of_birth());
            userFound.get().setPhoneNumber(user.getPhoneNumber());
            return userRepo.save(userFound.get());
        }

        return null;


    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
