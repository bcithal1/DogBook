package dogbook.model;

import dogbook.enums.AccessLevel;

import javax.persistence.*;

@Entity
public class DogOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private Integer dogId;

    @Column
    private AccessLevel accessLevel;

    public DogOwner(){

    }

    public DogOwner(Integer userId, Integer dogId, AccessLevel accessLevel){
        this.userId = userId;
        this.dogId = dogId;
        this.accessLevel = accessLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
