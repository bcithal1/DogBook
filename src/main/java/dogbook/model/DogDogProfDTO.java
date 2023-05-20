package dogbook.model;

public class DogDogProfDTO {
    private Dog dog;
    private DogProfile dogProfile;

    public DogDogProfDTO(Dog dog, DogProfile dogProfile) {
        this.dog = dog;
        this.dogProfile = dogProfile;
    }

    public DogProfile getDogProfile() {
        return dogProfile;
    }

    public void setDogProfile(DogProfile dogProfile) {
        this.dogProfile = dogProfile;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

}
