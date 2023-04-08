package dogbook.model;
import javax.persistence.*;

@Entity
public class DogTrick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trickId;

    @Column
    private Integer dogId;

    @Column
    private String trickName;

    //default constructor
    public DogTrick() {
    }

    public DogTrick(Integer dogId, String trickName) {
        this.dogId = dogId;
        this.trickName = trickName;
    }

    public Integer getTrickId() {
        return trickId;
    }

    public void setTrickId(Integer trickId) {
        this.trickId = trickId;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public String getTrickName() {
        return trickName;
    }

    public void setTrickName(String trickName) {
        this.trickName = trickName;
    }
}
