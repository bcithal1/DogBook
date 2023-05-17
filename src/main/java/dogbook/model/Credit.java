package dogbook.model;

import javax.persistence.*;
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer credit;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    public Credit() {
    }

    public Credit(Integer credit, User user) {

        this.credit = credit;
        this.user = user;
    }

    public Integer getCreditId() {
        return id;
    }

    public void setCreditId(Integer creditId) {
        this.id = creditId;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
