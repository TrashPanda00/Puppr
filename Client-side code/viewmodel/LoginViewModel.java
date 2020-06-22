package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The LoginViewModel class handles the logic behind the login view
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class LoginViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {

    private LocalModel model;
    private StringProperty username;
    private StringProperty password;
    private PropertyChangeAction<Object, Object> property;

    /**
     * A constructor setting up the local variables
     *
     * @param model an instance of the LocalModel interface
     */
    public LoginViewModel(LocalModel model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        property = new PropertyChangeProxy<>(this);
    }

    /**
     * Getter for the password property
     *
     * @return a reference to the password String property
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Getter for the username property
     *
     * @return a reference to the username String property
     */
    public StringProperty usernameProperty() {
        return username;
    }

    /**
     * A method checking if the login credentials are correct
     *
     * @return a boolean value equal to true if the credentials are correct and
     * to false otherwise
     */
    public boolean setCredentials() {
        User user = model.getUserByHandle(username.get());
        if (user == null) {
            return false;
        }

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert digest != null;
        byte[] encodedhash = digest.digest(password.get().getBytes(StandardCharsets.UTF_8));
        String finalhash = bytesToHex(encodedhash);
        return user.getPassword().equals(finalhash);
    }

    /**
     * A method signaling to the other classes that a user logged in as a guest
     */
    public void guestEnter() {
        property.firePropertyChange("guest", "guest", true);
    }

    /**
     * A method notifying the other classes that a normal user logged in
     */
    public void userLogin() {
        property.firePropertyChange("user", null, username.get());
    }

    /**
     * A method being called whenever a subject property changes and fires an event
     *
     * @param event the event that was fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        property.firePropertyChange(event);
    }

    /**
     * A method for adding a listener for this class
     *
     * @param listener      the listener that is being added
     * @param propertyNames the names of the properties for which the listener will listen for
     * @return a boolean value of true when a property change is heard
     */
    @Override
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    /**
     * A method removing a listener for certain properties for this class
     *
     * @param listener      the listener that is being removed
     * @param propertyNames the names of the properties that will no longer be listened by the listener
     * @return a boolean value indicating the removal of the listener
     */
    @Override
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }

    /**
     * Getter for a user object based on its handle
     *
     * @param handle the handle of the user object whose information is needed
     * @return an User object with the same handle as the specified value
     */
    public User getUserByHandle(String handle) {
        return model.getUserByHandle(handle);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
