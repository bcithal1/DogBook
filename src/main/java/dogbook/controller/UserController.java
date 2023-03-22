package dogbook.controller;

import dogbook.model.User;
import dogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/v1/users")
    @PreAuthorize("hasAuthority('ROLE_next-server')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        if (savedUser != null) {
//            return ResponseEntity.created(URI.create("/api/v1/users/" + savedUser.getId())).body(savedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/v1/users/{id}")
    @PreAuthorize("@authenticatedUserService.hasId(#id) or hasAuthority('ROLE_next-server')")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user){
        User savedUser = userService.updateUser(id, user);
        if(savedUser != null){
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        Optional<User> user = userService.getUserById(id);

        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<Object> getUserByEmail(@RequestParam(required = false) String email, @RequestParam(required = false) String providerName, @RequestParam(required = false) String providerAccountId){
        if(email != null) {
            User user = userService.getUserByEmail(email);
            return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
        }
        if(providerName != null && providerAccountId != null){
            User user = userService.getUserByProviderAccount(providerName, providerAccountId);
            return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
        }
        List<User> response = userService.getAllUsers();
        return response == null? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(response);
    }





}
