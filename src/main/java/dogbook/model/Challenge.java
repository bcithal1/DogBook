package dogbook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private LocalDate start_date;
    @Column
    private LocalDate target_date;


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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getTarget_date() {
        return target_date;
    }

    public void setTarget_date(LocalDate target_date) {
        this.target_date = target_date;
    }


}
