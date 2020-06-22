package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;


import java.sql.Date;
import java.time.LocalDate;

/**
 * The ManageProfileViewModel class handles the login behind the manageProfile view
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class ManageProfileViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private StringProperty gender;
    private StringProperty handle;
    private ObjectProperty<LocalDate> birthday;
    private StringProperty email;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty bio;
    private byte[] imageurl;
    private String changeableHandle;

    /**
     * A constructor setting the local variables and the listeners
     * @param model
     *          an instance of the LocalModel interface
     * @param homeViewModel
     *          an instance od the HomeViewModel class
     */
    public ManageProfileViewModel(LocalModel model, HomeViewModel homeViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addListener(this, "user");
        gender = new SimpleStringProperty();
        handle = new SimpleStringProperty();
        birthday = new SimpleObjectProperty<>();
        email = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        bio = new SimpleStringProperty();

    }

    /**
     * A method being called whenever a subject property changes and fires an event
     * @param event
     *        the event that was fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        changeableHandle = event.getValue2().toString();
    }

    /**
     * Setter for the user's gender
     * @param gender
     *          the gender of the user
     */
    public void setGender(String gender) {
        this.gender.set(gender);
    }

    /**
     * Getter for the local model
     * @return a reference to the model variable
     */
    public LocalModel getModel() {
        return model;
    }

    /**
     * Getter for the gender property
     * @return a reference to the gender String property
     */
    public StringProperty genderProperty() {
        return gender;
    }

    /**
     * Getter for the bio property
     * @return a reference to the bio String property
     */
    public StringProperty bioProperty() {
        return bio;
    }

    /**
     * Getter for the handle property
     * @return a reference to the handle String property
     */
    public StringProperty handleProperty() {
        return handle;
    }

    /**
     * Getter for the email property
     * @return a reference to the email String property
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Getter for the first name property
     * @return a reference to the last name String property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Getter for the last name property
     * @return a reference to the last name String property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Getter for the current user object
     * @return a reference to the current user handle
     */
    public User getUserInfo() {
        return model.getUserByHandle(changeableHandle);
    }

    /**
     * A method for editing the current user's info
     */
    public void editUserInfo() {

        User old = model.getUserByHandle(changeableHandle);
        String userType;
        if (imageurl == null) {
            imageurl = old.getImageURL();
        }
        if (old.getUserType().equals("admin")) {
            userType = "admin";
        } else {
            userType = "user";
        }
        User user = new User(changeableHandle, firstName.get(), lastName.get(), imageurl, old.getPassword(),
                email.get(), Date.valueOf(birthday.get()), gender.get(), old.getDogList(), bio.get(), userType, old.getStatus());

        model.editUser(user);
        homeViewModel.updateUserInfo(changeableHandle);
        homeViewModel.setPortrait();
    }

    /**
     * Setter for the image url
     * @param imageurl
     *          the new byte information for the profile picture
     */
    public void setImageurl(byte[] imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * Getter for the image url
     * @return a byte array containing the information for the profile picture
     */
    public byte[] getImageurl() {
        return imageurl;
    }

    /**
     * Getter for the birthday property
     * @return a reference to the birthday property
     */
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

}
