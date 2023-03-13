package dogbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dogbook.service.implementation.AccessLevel;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private String breed;
    @Column
    private String sex;
    @Column
    private String temperamentBody;
    @Column
    private String likesBody;
    @Column
    private Boolean altered;
    @Column
    private Integer weightLbs;
    @Column
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dogId", updatable = false, insertable = false)
    private List<DogOwner> owners;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTemperamentBody() {
        return temperamentBody;
    }

    public void setTemperamentBody(String temperamentBody) {
        this.temperamentBody = temperamentBody;
    }

    public String getLikesBody() {
        return likesBody;
    }

    public void setLikesBody(String likesBody) {
        this.likesBody = likesBody;
    }

    public Boolean getAltered() {
        return altered;
    }

    public void setAltered(Boolean altered) {
        this.altered = altered;
    }

    public Integer getWeightLbs() {
        return weightLbs;
    }

    public void setWeightLbs(Integer weightLbs) {
        this.weightLbs = weightLbs;
    }

    public List<DogOwner> getOwners() {
        return owners;
    }

    public void setOwners(List<DogOwner> owners) {
        this.owners = owners;
    }

    @JsonIgnore
    public Integer getPrimaryOwnerId(){
        // NOTE(Trystan): We are guaranteed to have a primary owner per the implementation of the dog creation.
        return this.owners.stream()
                .filter(owner -> owner.getAccessLevel() == AccessLevel.PRIMARY_OWNER)
                .findFirst().get().getUserId();
    }

    public Optional<DogOwner> getOwnerFromOwnerId(Integer ownerId) {
        return this.owners.stream()
                .filter(owner -> owner.getUserId() == ownerId)
                .findFirst();
    }
}
