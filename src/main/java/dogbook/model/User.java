package dogbook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Column
    private LocalDate date_of_birth;
    @Column
    private String gender;
    @Column
    private String profile_photo;

    @ManyToMany(mappedBy = "userSet", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("userSet")
    private Set<Challenge> challengeSet = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
//        comparing the class name
        if (getClass() != obj.getClass())
            return false;

        User other = (User) obj;
        if (this.first_name != other.first_name)
            return false;
        if (this.last_name!= other.last_name)
            return false;
        if (this.address != other.address)
            return false;
        if (this.phoneNumber!= other.phoneNumber)
            return false;
        return true;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public Set<Challenge> getChallengeSet() {
        return challengeSet;
    }

    public void setChallengeSet(Set<Challenge> challengeSet) {
        this.challengeSet = challengeSet;
    }
}
