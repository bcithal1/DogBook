package dogbook.model;

import javax.persistence.*;

public class UserProfile {

    @Id
    Integer id;
    @Column
    Integer profilePhotoId;
    @Column
    Integer bannerPhotoId;
    @OneToOne
    @JoinColumn(name="userId")
    User user;
    @Column
    String aboutMe;

    public UserProfile(Integer id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
