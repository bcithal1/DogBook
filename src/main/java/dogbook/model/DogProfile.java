package dogbook.model;

import javax.persistence.*;

@Entity(name = "dog_profile")
public class DogProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    Integer profilePhotoId;
    @Column
    Integer bannerPhotoId;
    @OneToOne
    @JoinColumn(name="dogId")
    Dog dog;

    @Column
    String temperament;
    @Column
    String bio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(Integer profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public Integer getBannerPhotoId() {
        return bannerPhotoId;
    }

    public void setBannerPhotoId(Integer bannerPhotoId) {
        this.bannerPhotoId = bannerPhotoId;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
