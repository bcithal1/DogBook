package dogbook.model;

import javax.persistence.*;

@Entity(name = "dog_profile")
public class DogProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
//    @OneToOne
//    @JoinColumn(name = "profilePhotoId")
//    Photo profilePhoto;
//    @OneToOne
//    @JoinColumn(name = "bannerPhotoId")
//    Photo bannerPhoto;
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

//    public Photo getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public void setProfilePhoto(Photo profilePhoto) {
//        this.profilePhoto = profilePhoto;
//    }
//
//    public Photo getBannerPhoto() {
//        return bannerPhoto;
//    }
//
//    public void setBannerPhoto(Photo bannerPhoto) {
//        this.bannerPhoto = bannerPhoto;
//    }
//
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
