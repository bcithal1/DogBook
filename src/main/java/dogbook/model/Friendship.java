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

    @Column
    private Integer connectedRecord;

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

    public Integer getFirstUser() {
        return primaryUserId;
    }

    public void setFirstUser(Integer firstUser) {
        this.primaryUserId = firstUser;
    }

    public Integer getSecondUser() {
        return secondaryUserId;
    }

    public void setSecondUser(Integer secondUser) {
        this.secondaryUserId = secondUser;
    }

    public Integer getConnectedRecord() {
        return connectedRecord;
    }

    public void setConnectedRecord(Integer connectedRecord) {
        this.connectedRecord = connectedRecord;
    }
}
