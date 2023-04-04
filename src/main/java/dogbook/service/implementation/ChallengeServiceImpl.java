package dogbook.service.implementation;

import dogbook.model.Challenge;
import dogbook.model.User;
import dogbook.model.UserChallengeRelation;
import dogbook.repository.ChallengeRepo;
import dogbook.repository.UserChallengeRelationRepo;
import dogbook.repository.UserRepo;
import dogbook.service.AuthenticatedUserService;
import dogbook.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    ChallengeRepo challengeRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    UserChallengeRelationRepo userChallengeRelationRepo;

    @Override
    public List<Challenge> getChallenges() {
        return challengeRepo.findAll();
    }

    @Override
    public Challenge createChallenges(Challenge challenge) {
        return challengeRepo.save(challenge);
    }

    @Override
    public Challenge getChallengeById(Integer id) {
        return challengeRepo.findById(id).get();
    }

    @Override
    public Challenge updateChallengeById(Integer id, Challenge challenge) {

        Optional<Challenge> challengeFound = challengeRepo.findById(id);
        if(challengeFound.isPresent()){
            challengeFound.get().setName(challenge.getName());
            challengeFound.get().setStartDate(challenge.getStartDate());
            challengeFound.get().setTargetDate(challenge.getTargetDate());
            return challengeRepo.save(challengeFound.get());
        }
        return null;
    }

    @Override
    public String deleteChallengeById(Integer id) {

//        only the user that has the challenge assigned can delete their own challenge

        Integer userId = authenticatedUserService.getId();
        Optional<Challenge> challengeFound = challengeRepo.findById(id);
        Optional<User> userFound = userRepo.findById(userId);

        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation->relation.getUser().getId()==userId && relation.getChallenge().getId()==id)
                .collect(Collectors.toList());


        if(userFound.isPresent() && challengeFound.isPresent() && !relationFound.isEmpty()){
//            only delete the relation but not the generic challenges
            userChallengeRelationRepo.deleteById(relationFound.get(0).getId());
            return "challenge # " + id + " is deleted";
        }

        return null;
    }

    @Override
    public Challenge assignChallengeToUser(Integer challengeId) {

        Integer userId = authenticatedUserService.getId();
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);
//        make sure same challenge can only be assigned to the same user only once
        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation->relation.getUser().getId()==userId && relation.getChallenge().getId()==challengeId)
                .collect(Collectors.toList());

        if(userFound.isPresent() && challengeFound.isPresent() && relationFound.isEmpty()){

            UserChallengeRelation relation = new UserChallengeRelation(userFound.get(), challengeFound.get(), "0", null);

            return userChallengeRelationRepo.save(relation).getChallenge();
        }
        return null;
    }

    @Override
    public Challenge updateUserChallengeStatus(Integer challengeId, Integer userId, String statusCode, LocalDate completedDate) {
        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);
        List<UserChallengeRelation> relationFound = userChallengeRelationRepo.findAll().stream()
                .filter(relation->relation.getUser().getId()==userId && relation.getChallenge().getId()==challengeId)
                .collect(Collectors.toList());

        if(userFound.isPresent() && challengeFound.isPresent() && !relationFound.isEmpty()){

            UserChallengeRelation relationToUpdate = relationFound.get(0);
            relationToUpdate.setStatusCode(statusCode);
            relationToUpdate.setCompletedDate(completedDate);
            return userChallengeRelationRepo.save(relationToUpdate).getChallenge();
        }
        return null;
    }


}
