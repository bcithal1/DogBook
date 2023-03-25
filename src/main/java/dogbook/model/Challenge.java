package dogbook.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate targetDate;


    @OneToMany(mappedBy = "challenge")
    Set<UserChallengeRelation> userChallengeRelations = new HashSet<>();


    public void addUserChallengeRelationship(UserChallengeRelation userChallengeRelation){
        userChallengeRelations.add(userChallengeRelation);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }


}
