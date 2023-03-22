package dogbook.service;

import dogbook.model.Challenge;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeService {
    List<Challenge> getChallenges();

    Challenge createChallenges(Challenge challenge);

    Challenge getChallengeById(Integer id);

    Challenge updateChallengeById(Integer id, Challenge challenge);

    String deleteChallengeById(Integer id);

    Challenge assignChallengeToUser(Integer challengeId, Integer userId);

    Challenge updateUserChallengeStatus(Integer challengeId, Integer userId, String statusCode, LocalDate completedDate);
}
