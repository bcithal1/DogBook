package dogbook.service;

import dogbook.model.Challenge;
import dogbook.model.User;
import dogbook.model.UserChallengeRelation;
import dogbook.repository.ChallengeRepo;
import dogbook.repository.UserChallengeRelationRepo;
import dogbook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChallengeService {
    @Autowired
    ChallengeRepo challengeRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    UserChallengeRelationRepo userChallengeRelationRepo;

    public List<Challenge> getChallenges() {
        return challengeRepo.findAll();
    }

    public Challenge createChallenges(Challenge challenge) {
        return challengeRepo.save(challenge);
    }

    public Challenge getChallengeById(Integer id) {
        Challenge challenge = null;

        if (challengeRepo.findById(id).isPresent())
             challenge = challengeRepo.findById(id).get();

        return challenge;
    }

    public Challenge updateChallengeById(Integer id, Challenge challenge) {

        Optional<Challenge> challengeFound = challengeRepo.findById(id);
        if (challengeFound.isPresent()) {
            challengeFound.get().setName(challenge.getName());
            challengeFound.get().setStartDate(challenge.getStartDate());
            challengeFound.get().setTargetDate(challenge.getTargetDate());
            return challengeRepo.save(challengeFound.get());
        }
        return null;
    }

    public String deleteChallengeById(Integer id) {

        // only the user that has the challenge assigned can delete their own challenge

        Integer userId = authenticatedUserService.getId();
        Optional<Challenge> challengeFound = challengeRepo.findById(id);
        Optional<User> userFound = userRepo.findById(userId);

        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation -> relation.getUser().getId().equals(userId) && relation.getChallenge().getId().equals(id))
                .collect(Collectors.toList());


        if (userFound.isPresent() && challengeFound.isPresent() && !relationFound.isEmpty()) {
            // only delete the relation but not the generic challenges
            userChallengeRelationRepo.deleteById(relationFound.get(0).getId());
            return "challenge # " + id + " is deleted";
        }

        return null;
    }

    public Challenge assignChallengeToUser(Integer challengeId) {

        Integer userId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);
        // make sure same challenge can only be assigned to the same user only once
        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation -> relation.getUser().getId().equals(userId) && relation.getChallenge().getId().equals(challengeId))
                .collect(Collectors.toList());

        if (userFound.isPresent() && challengeFound.isPresent() && relationFound.isEmpty()) {

            UserChallengeRelation relation = new UserChallengeRelation(userFound.get(), challengeFound.get(), "0", null);

            return userChallengeRelationRepo.save(relation).getChallenge();
        }
        return null;
    }

    public Challenge updateUserChallengeStatus(Integer challengeId, Integer userId, String statusCode, LocalDate completedDate) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);
        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation -> relation.getUser().getId().equals(userId) && relation.getChallenge().getId().equals(challengeId))
                .collect(Collectors.toList());

        if (userFound.isPresent() && challengeFound.isPresent() && !relationFound.isEmpty()) {

            UserChallengeRelation relationToUpdate = relationFound.get(0);
            relationToUpdate.setStatusCode(statusCode);
            relationToUpdate.setCompletedDate(completedDate);
            return userChallengeRelationRepo.save(relationToUpdate).getChallenge();
        }
        return null;
    }

    public Challenge assignChallengeToAnyUser(Integer challengeId, Integer userId) {

        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);
        // make sure same challenge can only be assigned to the same user only once
        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation -> relation.getUser().getId().equals(userId) && relation.getChallenge().getId().equals(challengeId))
                .collect(Collectors.toList());

        if (userFound.isPresent() && challengeFound.isPresent() && relationFound.isEmpty()) {

            UserChallengeRelation relation = new UserChallengeRelation(userFound.get(), challengeFound.get(), "0", null);

            return userChallengeRelationRepo.save(relation).getChallenge();
        }
        return null;


    }

    public List<Challenge> getChallengesByEventId(Integer eventId) {

        List<Challenge> challengeListFound = challengeRepo.findByEventId(eventId);

        if(!challengeListFound.isEmpty()){
            return challengeListFound;
        }
        return null;
    }
}
