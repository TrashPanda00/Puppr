package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

/**
 * The ChangePasswordViewModel class handles the logic behind the changePassword view. It sends
 * the new password information to the local model class to be handled and finally updated in the database.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class ChangePasswordViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private LoginViewModel loginViewModel;
    private StringProperty password;
    private StringProperty newPassword;
    private StringProperty repeatPassword;
    private String handle;

    /**
     * A constructor setting the values for the class variables and the listener for the LoginViewModel property changes
     * @param model
     *          an instance of the LocalModel interface
     * @param loginViewModel
     *          an instance of the LoginViewModel interface
     */
    public ChangePasswordViewModel(LocalModel model, LoginViewModel loginViewModel) {
        this.model = model;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addListener(this, "user");
        password = new SimpleStringProperty();
        newPassword = new SimpleStringProperty();
        repeatPassword = new SimpleStringProperty();
    }

    /**
     * Getter for the password property
     * @return a reference to the password String property
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Getter for the new password property
     * @return a reference to the newPassword String property
     */
    public StringProperty newPasswordProperty() {
        return newPassword;
    }

    /**
     * Getter for the repeated password property
     * @return a reference to the repeatPassword String property
     */
    public StringProperty repeatPasswordProperty() {
        return repeatPassword;
    }

    /**
     * Getter for the user handle
     * @return a reference to the user's handle value
     */
    public String getHandle() {
        return handle;
    }

    /**
     * A method being called when a subject property changes and fires an event
     * @param event
     *          the event fired by the property changed and heard here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        handle = event.getValue2().toString();
    }

    /**
     * A method for saving the new password data and sending it to the model class
     */
    public void savePassword() {
        User user = model.getUserByHandle(handle);
        model.editUser(new User(user.getHandle(), user.getName(), user.getLastname(), user.getImageURL(),
                newPassword.get(), user.getEmail(), user.getBirthday(), user.getGender(), user.getDogList(), user.getBio(), "user", user.getStatus()));
    }

    /**
     * Getter for the current password
     * @return a reference to the current password value for the user
     */
    public String getUserPassword() {

        return model.getUserByHandle(handle).getPassword();
    }

}
