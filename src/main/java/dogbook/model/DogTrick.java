package dogbook.model;
import javax.persistence.*;

@Entity
public class DogTrick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trickID;

    @Column
    private Integer dogId;

    @Column
    private String trickName;

    public DogTrick(Integer dogId, String trickName) {
        this.dogId = dogId;
        this.trickName = trickName;
    }

    public Integer getTrickID() {
        return trickID;
    }

    public void setTrickID(Integer trickID) {
        this.trickID = trickID;
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
