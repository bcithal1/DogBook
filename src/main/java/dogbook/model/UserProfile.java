package dogbook.model;

import javax.persistence.*;

@Entity
public class UserProfile {

    @Id
    Integer id;
    @Column
    Integer profilePhotoId;
    @Column
    Integer bannerPhotoId;
    @Column
    String aboutSection;

    public UserProfile() {
    }

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

    public String getAboutSection() {
        return aboutSection;
    }

    public void setAboutSection(String aboutSection) {
        this.aboutSection = aboutSection;
    }
}
