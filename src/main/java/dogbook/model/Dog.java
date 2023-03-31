package dogbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dogbook.enums.Size;
import dogbook.enums.AccessLevel;
import dogbook.enums.Sex;

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
    private Integer breedId;
    @Column
    private Sex sex;
    @Column
    private Boolean altered;
    @Column
    private Size size;
    @Column
    private Integer weightLbs;
    @Column
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dogId", updatable = false, insertable = false)
    private List<DogOwner> owners;
    @Column
    @ElementCollection
    private List<Integer> photoIds;

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

    public Integer getBreedId() {
        return breedId;
    }

    public void setBreedId(Integer breedId) {
        this.breedId = breedId;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean getAltered() {
        return altered;
    }

    public void setAltered(Boolean altered) {
        this.altered = altered;
    }

    public Size getSize(){
        return this.size;
    }

    public void setSize(Size size){
        this.size = size;
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

    public List<Integer> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Integer> photoIds) {
        this.photoIds = photoIds;
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
