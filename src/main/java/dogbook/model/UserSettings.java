package dogbook.model;

import dogbook.Enum.FriendListVisibility;

import javax.persistence.*;

import static dogbook.Enum.FriendListVisibility.ALL;

@Entity
public class UserSettings {

    @Id
    private Integer settingsId;

    @Column
    private FriendListVisibility friendListView;

    public UserSettings(Integer settingsId) {
        this.settingsId = settingsId;
        this.friendListView = ALL;
    }

    public Integer getId() {
        return settingsId;
    }

    public void setId(Integer id) {
        this.settingsId = id;
    }

    public FriendListVisibility friendListView() {
        return friendListView;
    }

    public void setVisabilityFriendList(FriendListVisibility visabilityFriendList) {
        this.friendListView = visabilityFriendList;
    }
}
