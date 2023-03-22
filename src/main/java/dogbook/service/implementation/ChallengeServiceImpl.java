package dogbook.service.implementation;

import dogbook.model.Challenge;
import dogbook.model.User;
import dogbook.model.UserChallengeRelation;
import dogbook.repository.ChallengeRepo;
import dogbook.repository.UserChallengeRelationRepo;
import dogbook.repository.UserRepo;
import dogbook.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    ChallengeRepo challengeRepo;
    @Autowired
    UserRepo userRepo;

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
            challengeFound.get().setStart_date(challenge.getStart_date());
            challengeFound.get().setTarget_date(challenge.getTarget_date());
            return challengeRepo.save(challengeFound.get());
        }
        return null;
    }

    @Override
    public String deleteChallengeById(Integer id) {
        Optional<Challenge> challengeFound = challengeRepo.findById(id);
        if(challengeFound.isPresent()){
            challengeRepo.deleteById(id);
            return "challenge # " + id + " is deleted";
        }

        return null;
    }

    @Override
    public Challenge assignChallengeToUser(Integer challengeId, Integer userId) {

        Optional<User> userFound = userRepo.findById(userId);
        Optional<Challenge> challengeFound = challengeRepo.findById(challengeId);

        if(userFound.isPresent() && challengeFound.isPresent()){

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
