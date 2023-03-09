package dogbook.model;

import javax.persistence.*;

@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer userId;
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
}
