package dogbook.model;

import java.util.List;

public class FriendRequestWithUser {
    private FriendRequest friendRequest;
    private User user;
    private UserProfile userProfile;
    private List<User> mutualFriends;

    public FriendRequestWithUser(FriendRequest friendRequest, User user, UserProfile userProfile, List<User> mutualFriends) {
        this.friendRequest = friendRequest;
        this.user = user;
        this.userProfile = userProfile;
        this.mutualFriends = mutualFriends;
    }

    public List<User> getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(List<User> mutualFriends) {
        this.mutualFriends = mutualFriends;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
