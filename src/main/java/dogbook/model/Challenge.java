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
    @Column
    private LocalDate completed_date;
    @Column
    private Integer status_code;


    @ManyToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name="User_Challenge_table",
            joinColumns = @JoinColumn(name="challenge_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="User_id", referencedColumnName = "id")


    )
    @JsonIgnoreProperties("challengeSet")
    private Set<User> userSet = new HashSet<>();


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
//        comparing the class name
        if (getClass() != obj.getClass())
            return false;

        Challenge other = (Challenge) obj;
        if (this.name != other.name)
            return false;
        if (this.start_date!= other.start_date)
            return false;
        if (this.target_date != other.target_date)
            return false;
        if (this.status_code!= other.status_code)
            return false;
        return true;
    }

    public void assignUserToChallenge(User user){
        userSet.add(user);
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

    public LocalDate getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(LocalDate completed_date) {
        this.completed_date = completed_date;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}
