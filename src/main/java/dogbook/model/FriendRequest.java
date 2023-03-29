package dogbook.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Sender_ID")
    private Integer senderId;

    @Column(name="Receiver_ID")
    private Integer receiverId;

    @Column
    private Date createDate;

    public FriendRequest() {
    }

    public FriendRequest(Integer senderId, Integer receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setRequestReceiver(Integer requestReceiver) {
        this.receiverId = requestReceiver;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
