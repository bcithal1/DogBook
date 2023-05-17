package dogbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class UserChallengeRelation {

    @Id
    @Column(name = "user_Challenge_relation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="challengeId")
    @JsonIgnore
    private Challenge challenge;


    private String statusCode;

    private LocalDate completedDate;


    public UserChallengeRelation() {
    }

    public UserChallengeRelation(User user, Challenge challenge, String statusCode, LocalDate completedDate) {

        this.user = user;
        this.challenge = challenge;
        this.statusCode = statusCode;
        this.completedDate = completedDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
}
