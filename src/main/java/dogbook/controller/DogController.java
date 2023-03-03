package dogbook.controller;

import dogbook.model.Dog;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogController {

    @Autowired
    DogService dogService;


    @PostMapping("/dogbook/dog")
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog){
        Dog response = dogService.createDog(dog);
        return response==null? new ResponseEntity<>(HttpStatus.BAD_REQUEST): ResponseEntity.ok(response);
    }



}
