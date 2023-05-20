package dogbook.model;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column
    private Integer authorId;

    @Column
    private Integer commentId;

    @Column
    private Integer likeCount;

    @Column
    private Integer commentCount;

    @Column
    private String message;

    @ElementCollection
    @CollectionTable(name = "post_tagged_users", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "user_id")
    private List<Integer> taggedUserId;

    @ElementCollection
    @CollectionTable(name = "post_tagged_dogs", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "dog_id")
    private List<Integer> taggedDogId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dateTime;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public List<Integer> getTaggedUserId() {
        return taggedUserId;
    }

    public void setTaggedUserId(List<Integer> taggedUserId) {
        this.taggedUserId = taggedUserId;
    }

    public List<Integer> getTaggedDogId() {
        return taggedDogId;
    }

    public void setTaggedDogId(List<Integer> taggedDogId) {
        this.taggedDogId = taggedDogId;
    }
}
