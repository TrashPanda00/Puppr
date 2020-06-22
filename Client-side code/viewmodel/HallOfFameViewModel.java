package viewmodel;

import model.Dog;
import model.LocalModel;
import model.User;

import java.util.ArrayList;

/**
 * The HallOfFameViewModel handles the login behind the hallOfFame view
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class HallOfFameViewModel {

    private LocalModel model;
    private HomeViewModel homeViewModel;

    /**
     * A constructor setting up the local variables
     * @param model
     *          a reference to the LocalModel interface
     * @param homeViewModel
     *          a reference to the HomeViewModel class
     */
    public HallOfFameViewModel (LocalModel model,HomeViewModel homeViewModel){

        this.model=model;
        this.homeViewModel=homeViewModel;
    }

    /**
     * Getter for a list of all the dogs
     * @return an ArrayList of Dog data type containing all the dog
     * information received from the LocalModel
     */
    public ArrayList<Dog> allDogs(){
        return model.getDogList();
    }

    /**
     * Getter for a User object based on its handle
     * @param handle
     *          the handle of the user whose information is needed
     * @return an User object with the handle equal to the specified value
     */
    public User getUserByHandle(String handle){
        return model.getUserByHandle(handle);
    }

    /**
     * A method for opening a user's profile
     * @param handle
     *          the handle of the user whose profile page will be opened
     */
    public void openUserProfile(String handle) {
        homeViewModel.openUserProfile(handle);
    }

}
