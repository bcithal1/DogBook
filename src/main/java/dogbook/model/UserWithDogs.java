package dogbook.model;

import java.util.List;

public class UserWithDogs {
    private User user;
    private List<Dog> dogs;

    public UserWithDogs(User user, List<Dog> dogs) {
        this.user = user;
        this.dogs = dogs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
}
