package dogbook.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Friendship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Date createDate;

    @Column
    private Integer primaryUserId;

    @Column
    private Integer secondaryUserId;

    public Friendship() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createDate = createdDate;
    }

    public Integer getPrimaryUserId() {
        return primaryUserId;
    }

    public void setPrimaryUserId(Integer firstUser) {
        this.primaryUserId = firstUser;
    }

    public Integer getSecondaryUserId() {
        return secondaryUserId;
    }

    public void setSecondaryUserId(Integer secondUser) {
        this.secondaryUserId = secondUser;
    }

}
