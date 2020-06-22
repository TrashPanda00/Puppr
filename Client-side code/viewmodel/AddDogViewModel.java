package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Dog;
import model.ImageConverter;
import model.LocalModel;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The AddDogViewModel class implements the functionality and logic behind the
 * addDog view. The class sends the new Dog objects further to the model where it will
 * be sent to the server and finally saved as information in the database.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class AddDogViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private StringProperty dogName;
    private StringProperty dogInfo;
    private byte[] imageurl;
    private String handle;

    /**
     * A constructor setting up the class variables and the listener for HomeViewModel events
     *
     * @param model         an instance of the LocalModel interface
     * @param homeViewModel an instance of the HomeViewModel class
     */
    public AddDogViewModel(LocalModel model, HomeViewModel homeViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        homeViewModel.addListener(this, "user");
        dogName = new SimpleStringProperty();
        dogInfo = new SimpleStringProperty();
        imageurl = null;
    }

    /**
     * Getter for the dogName
     *
     * @return a reference to the dogName String property
     */
    public StringProperty dogNameProperty() {
        return dogName;
    }

    /**
     * Getter for the dogInfo
     * @return a reference to the dogInfo String property
     */
    public StringProperty dogInfoProperty() {
        return dogInfo;
    }

    /**
     * A method for sending a new dog's information to the local model
     */
    public void addDog() {
        if(imageurl == null)
        {
            try
            {
                imageurl = ImageConverter.ImageToByte(new File("src/resources/defaultDog.jpg"));
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        Dog dog = new Dog(dogName.get(), imageurl, dogInfo.get(), handle, 0);
        model.addDog(dog);
        homeViewModel.updateDogs();
    }

    /**
     * Setter for the imageurl variable
     * @param imageurl
     *          the new value for the imageurl variable
     */
    public void setImageurl(byte[] imageurl) {
        this.imageurl = imageurl;
    }

    /**
     * Getter for the imageurl variable
     * @return a reference to the value of the imageurl
     */
    public byte[] getImageurl() {
        return imageurl;
    }

    /**
     * A method being called whenever the subject property changes and fires an event
     * @param event
     *          the event fire by the property change
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        handle = event.getValue2().toString();
    }

}
