package dogbook.controller;

import dogbook.model.Provider;
import dogbook.model.User;
import dogbook.service.ProviderService;
import dogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class ProviderController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProviderService providerService;

    @PostMapping("/api/v1/users/{id}/providers")
    @PreAuthorize("hasAuthority('ROLE_next-server')")
    public ResponseEntity<Provider> createUser(@PathVariable Integer id, @RequestBody Provider provider) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        provider.setUserId(user.get().getId());

        Provider savedProvider = providerService.createProvider(provider);
        if(savedProvider != null){
            return ResponseEntity.created(URI.create("/api/v1/users/" + id + "/providers/" + savedProvider.getId()))
                    .body(savedProvider);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/users/{id}/providers")
    @PreAuthorize("hasAuthority('ROLE_next-server')")
    public ResponseEntity<List<Provider>> getProviders(@PathVariable Integer id){
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(providerService.getAllProvidersByUserId(id));
    }
}
