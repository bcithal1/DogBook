package dogbook.model;

public class FriendRequestWithUser {
    private FriendRequest friendRequest;
    private User user;
    private UserProfile userProfile;

    public FriendRequestWithUser(FriendRequest friendRequest, User user, UserProfile userProfile) {
        this.friendRequest = friendRequest;
        this.user = user;
        this.userProfile = userProfile;
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
