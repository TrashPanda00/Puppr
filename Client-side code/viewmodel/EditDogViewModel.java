package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.Dog;
import model.LocalModel;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

/**
 * The EditDogViewModel class handles the functionality and logic behind the editDog view
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class EditDogViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private ProfileViewModel profileViewModel;

    private StringProperty dogName;
    private StringProperty dogInfo;
    private byte[] imageurl;
    private String handle;
    private Dog dog;
    private int dogId;

    /**
     * A constructor setting the local variables and the listeners
     *
     * @param model            an instance of the LocalModel instance
     * @param homeViewModel    an instance of the HomeViewModel class
     * @param profileViewModel an instance of the ProfileViewModel class
     */
    public EditDogViewModel(LocalModel model, HomeViewModel homeViewModel, ProfileViewModel profileViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        this.profileViewModel = profileViewModel;
        this.homeViewModel.addListener(this, "user");
        this.profileViewModel.addListener(this, "editDog");
        dogName = new SimpleStringProperty();
        dogInfo = new SimpleStringProperty();
    }

    /**
     * Getter for the dog's name property
     *
     * @return a reference to the dog name String property
     */
    public StringProperty dogNameProperty() {
        return dogName;
    }

    /**
     * Getter for the dog info property
     *
     * @return a reference to the dog info String property
     */
    public StringProperty dogInfoProperty() {
        return dogInfo;
    }

    /**
     * A method for editing the dog's information and sending it to the LocalModel
     */
    public void editDog() {
        Dog old = dog;
        if (imageurl == null) {
            imageurl = old.getImageURL();
        }
        Dog newDog = new Dog(old.getDogId(), dogName.get(), imageurl, dogInfo.get(), handle, old.getLikes());
        model.editDog(newDog);
        homeViewModel.updateDogs();
    }

    /**
     * Setter for the dog's profile image
     *
     * @param imageurl the new url for the dog's profile picture
     */
    public void setImageurl(byte[] imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * Getter for the dog's profile image url
     *
     * @return a byte array containing the information for the
     * dog's profile image
     */
    public byte[] getImageurl() {
        return imageurl;
    }

    /**
     * Getter for a dog object based on its id
     *
     * @param dogId
     *          the id of the dog whose information is needed
     * @return a Dog object with the specified id value
     */
    public Dog getDogById(int dogId) {

        for (Dog dog : model.getDogList()) {
            if (dog.getDogId() == dogId) {
                return dog;
            }
        }
        return null;
    }

    /**
     * A method being called whenever the subject property changes and fires an event
     * @param event
     *          the event fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        Platform.runLater(() -> {
            switch (event.getPropertyName()) {
                case "user":
                    handle = event.getValue2().toString();
                    break;
                case "editDog":
                    dog = (Dog) event.getValue2();
                    dogName.set(dog.getName());
                    dogInfo.set(dog.getInfo());
                    dogId = ((Dog) event.getValue2()).getDogId();
                    break;
            }
        });
    }

    /**
     * Getter for the current Dog object that is being edited
     * @return a reference to the current dog object
     */
    public Dog getDog() {
        return dog;
    }
}

