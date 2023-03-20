package dogbook.service.implementation;

import dogbook.model.Challenge;
import dogbook.model.User;
import dogbook.repository.ChallengeRepo;
import dogbook.repository.UserRepo;
import dogbook.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    ChallengeRepo challengeRepo;
    @Autowired
    private UserRepo userRepo;

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
            challengeFound.get().setCompleted_date(challenge.getCompleted_date());
            challengeFound.get().setStart_date(challenge.getStart_date());
            challengeFound.get().setTarget_date(challenge.getTarget_date());
            challengeFound.get().setStatus_code(challenge.getStatus_code());
            challengeFound.get().setUserSet(challenge.getUserSet());
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
            challengeFound.get().assignUserToChallenge(userFound.get());
            return challengeRepo.save(challengeFound.get());
        }
        return null;
    }


}
