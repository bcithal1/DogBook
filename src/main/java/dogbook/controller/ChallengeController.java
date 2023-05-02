package dogbook.controller;

import
        dogbook.model.Challenge;
import dogbook.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ChallengeController {

    @Autowired
    ChallengeService challengeService;

    @GetMapping("/api/v1/challenges")
    public ResponseEntity<List<Challenge>> getAllChallenges(){
        List<Challenge> challengesList = challengeService.getChallenges();
        return challengesList==null? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(challengesList);
    }

    @PostMapping("/api/v1/challenges")
    public ResponseEntity<Challenge> createChallenges(@RequestBody Challenge challenge){
        Challenge response = challengeService.createChallenges(challenge);
        return response==null? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/challenges/{id}")
    public ResponseEntity<Challenge> getChallengeById(@PathVariable Integer id){
        Challenge response = challengeService.getChallengeById(id);
        return response==null? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/challenges/{id}")
    public ResponseEntity<Challenge> updateChallengeById(@PathVariable Integer id, @RequestBody Challenge challenge){
        Challenge response = challengeService.updateChallengeById(id, challenge);
        return response==null? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/challenges/{id}")
    public ResponseEntity<String> deleteChallengeById(@PathVariable Integer id){
        String response = challengeService.deleteChallengeById(id);
        return response==null? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(response);
    }


    @PutMapping("/api/v1/challenges/assign/{challengeId}")
    public ResponseEntity<Challenge> assignChallengeToUser(@PathVariable Integer challengeId){
        Challenge response = challengeService.assignChallengeToUser(challengeId);
        return response==null? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(response);
    }


    @PutMapping("/api/v1/challenges/{challengeId}/{userId}/{statusCode}/{completedDate}")
    public ResponseEntity<Challenge> updateUserChallengeStatus(@PathVariable Integer challengeId, @PathVariable Integer userId, @PathVariable String statusCode, @PathVariable String completedDate){
        Challenge response = challengeService.updateUserChallengeStatus(challengeId, userId, statusCode, LocalDate.parse(completedDate));
        return response==null? new ResponseEntity<>(HttpStatus.NOT_FOUND): ResponseEntity.ok(response);
    }

}
