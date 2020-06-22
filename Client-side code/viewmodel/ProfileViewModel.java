package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Dog;
import model.LocalModel;
import model.Post;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.util.ArrayList;

/**
 * The ProfileViewModel class handles the login behind the profile view
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class ProfileViewModel implements LocalListener<Object, Object>,
        LocalSubject<Object, Object> {

    private LocalModel model;
    private StringProperty handle;
    private StringProperty name;
    private StringProperty wall;
    private StringProperty likesCount;
    private StringProperty bio;
    private PropertyChangeAction<Object, Object> property;
    private HomeViewModel homeViewModel;
    private String changeableHandle;
    private String mainUser;
    private boolean isGuest;
    private boolean isAdmin;

    /**
     * A constructor setting the local variables and the listeners
     *
     * @param model         an instance of the LocalModel interface
     * @param homeViewModel an instance of the HomeViewModel class
     */
    public ProfileViewModel(LocalModel model, HomeViewModel homeViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addListener(this);
        handle = new SimpleStringProperty();
        name = new SimpleStringProperty();
        wall = new SimpleStringProperty();
        likesCount = new SimpleStringProperty();
        bio = new SimpleStringProperty();
        property = new PropertyChangeProxy<>(this);
        isGuest = homeViewModel.isGuest();
    }

    /**
     * Getter for the user's handle
     *
     * @return a reference to the user's handle
     */
    public String getHandle() {
        return handle.get();
    }

    /**
     * Getter for the user's handle property
     *
     * @return a reference to the user's handle property
     */
    public StringProperty handleProperty() {
        return handle;
    }

    /**
     * Getter for the user's name
     *
     * @return a reference to the user's name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Getter for the user's wall property
     *
     * @return a reference to the user's wall property
     */
    public StringProperty wallProperty() {
        return wall;
    }

    /**
     * Getter for the likes count property
     *
     * @return a reference to the likesCount property
     */
    public StringProperty likesCountProperty() {
        return likesCount;
    }

    /**
     * Getter for the name property
     *
     * @return a reference to the name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Getter for the bio property
     *
     * @return a reference to the user's bio property
     */
    public StringProperty bioProperty() {
        return bio;
    }

    /**
     * A method for adding a listener for this class
     * @param listener
     *        the listener that is being added
     * @param propertyNames
     *        the names of the properties for which the listener will listen for
     * @return a boolean value of true when a property change is heard
     */
    @Override
    public boolean addListener(
            GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    /**
     * A method removing a listener for certain properties for this class
     * @param listener
     *        the listener that is being removed
     * @param propertyNames
     *        the names of the properties that will no longer be listened by the listener
     * @return a boolean value indicating the removal of the listener
     */
    @Override
    public boolean removeListener(
            GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }

    /**
     * A method being called whenever a subject property changes and fires an event
     * @param event
     *        the event that was fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {

        Platform.runLater(() -> {
            switch ((event.getPropertyName())) {
                case "user":
                    handle.set(" @" + event.getValue2());
                    changeableHandle = event.getValue2().toString();
                    mainUser = event.getValue2().toString();
                    name.set(event.getValue1().toString());
                    wall.set(event.getValue2().toString() + "'s Wall");
                    likesCount.set(event.getValue2().toString() + "'s posts have been liked " + getNumberOfLikes(event.getValue2() + "") + " times.");
                    bio.set(getUserByHandle(event.getValue2() + "").getBio());
                    property.firePropertyChange("user", null, mainUser);
                    break;
                case "bio":
                    bio.set(event.getValue2() + "");
                    break;
                case "post":
                    property.firePropertyChange("post", null, "change");
                    break;
                case "dog":
                    property.firePropertyChange("dog", null, "changeDogs");
                    break;
                case "otherUser":
                    User user = getUserByHandle(event.getValue2() + "");
                    handle.set(" @" + user.getHandle());
                    changeableHandle = event.getValue1() + "";
                    //changeable handle is used to store the current logged in user handle, not the view profile handle
                    name.set(user.getName() + " " + user.getLastname());
                    wall.set(user.getHandle() + "'s Wall");
                    likesCount.set(user.getHandle() + "'s posts have been liked " + getNumberOfLikes(event.getValue2() + "") + " times.");
                    bio.set(user.getBio());
                    property.firePropertyChange("otherUser", changeableHandle, event.getValue2());
                    break;
                case "admin":
                    isAdmin = (boolean) event.getValue2();
                    break;
                case "guest":
                    isGuest = (boolean) event.getValue2();
                    break;

            }
        });
    }

    /**
     * A method for deleting a dog from the user's profile
     *
     * @param dog the dog object that is to be deleted
     */
    public void deleteDog(Dog dog) {

        model.removeDog(dog);
    }

    /**
     * Getter for the current user's handle
     *
     * @return a reference to the current user's handle
     */
    public String getChangeableHandle() {
        return changeableHandle;
    }

    /**
     * Getter for the list of posts made by the user
     *
     * @param handle the user's handle
     * @return an ArrayList of Post data type containing all the posts made by the specified user
     */
    public ArrayList<Post> getPostsForUser(String handle) {
        return model.getPostsForUser(handle);
    }

    /**
     * Getter for all the dogs linked to the user
     *
     * @param handle a reference to the user's handle
     * @return an ArrayList of Dog data type containing all the dogs linked to the specified user
     */
    public ArrayList<Dog> getDogsForUser(String handle) {

        return model.getDogsForUser(handle);
    }

    /**
     * Getter for a user object based on its handle
     *
     * @param handle the handle of the user whose information is needed
     * @return an User object with the same handle as the specified one
     */
    public User getUserByHandle(String handle) {

        return model.getUserByHandle(handle);
    }

    /**
     * A method checking is the current user is a guest type user
     *
     * @return a boolean value equal to true is the user is a guest and
     * to false otherwise
     */
    public boolean isGuest() {
        return isGuest;
    }

    /**
     * A method for blocking a user and preventing them from logging in
     */
    public void blockUser() {

        User old = model.getUserByHandle(getHandle().replace(" @", ""));
        if (old.getStatus().equalsIgnoreCase("unblocked")) {
            User newUser = new User(old.getHandle(), old.getName(), old.getLastname(), old.getImageURL(), old.getPassword(), old.getEmail(),
                    old.getBirthday(), old.getGender(), old.getDogList(), old.getBio(), old.getUserType(), "blocked");

            model.editUser(newUser);
            System.out.println(getHandle().replace(" @", "") + " is blocked");
        } else {
            User newUser = new User(old.getHandle(), old.getName(), old.getLastname(), old.getImageURL(), old.getPassword(), old.getEmail(),
                    old.getBirthday(), old.getGender(), old.getDogList(), old.getBio(), old.getUserType(), "unblocked");

            model.editUser(newUser);
            System.out.println(getHandle().replace(" @", "") + " is unblocked");
        }
    }

    /**
     * A method checking if the current user is an admin
     * @return a boolean value equal to true is they are an admin and false
     * otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * A method for setting a comment to a post
     * @param post
     *          the post that the comment was added to
     */
    public void setComment(Post post) {
        homeViewModel.setComment(post);
    }

    /**
     * A method notifying the other classes that a dog object was modified
     * @param dog
     *          the dog object that was modified
     */
    public void editDog(Dog dog) {
        property.firePropertyChange("editDog", null, dog);
    }

    /**
     * A method notifying the other classes that a post object was modified
     * @param post
     *          the post object that was modified
     */
    public void editPost(Post post) {
        property.firePropertyChange("editPost", false, post);
    }

    /**
     * A method for deleting a post
     * @param post
     *          the post that is to be deleted
     */
    public void deletePost(Post post) {
        model.removePost(post);
    }

    /**
     * A method notifying the other classes that a post object was added
     * @param post
     *          the post object that was added
     */
    public void setPost(Post post) {

        property.firePropertyChange("comment", null, post);
    }

    /**
     * A method for liking a post
     * @param postId
     *          the is of the post that was liked
     * @param handle
     *          the handle of the user who liked it
     */
    public void likePost(int postId, String handle) {
        homeViewModel.likePost(postId, handle);
    }

    /**
     * A method for opening a post view
     * @param postId
     *          the id of the post the view will be opened for
     */
    public void openPost(int postId) {

        property.firePropertyChange("viewPostFromProfile", changeableHandle, postId);
    }

    /**
     * Getter for a list of all the post likes
     * @return an ArrayList containing all the information about post likes
     */
    public ArrayList<String> getAllLikes() {
        return model.getLikes();
    }

    /**
     * A method calculating the total number of post likes for a user
     * @param handle
     *          the handle of the user the likes are calculated for
     * @return the total number of likes for the user in integer form
     */
    public int getNumberOfLikes(String handle) {

        ArrayList<Post> posts = model.getPostsForUser(handle);
        int likes = 0;

        for (Post post : posts) {
            likes += post.getLikes();
        }
        return likes;
    }

    /**
     * A method for liking a dog card
     * @param dogId
     *          the id of the dog that was liked
     * @param handle
     *          the handle of the user who liked the dog
     */
    public void likeDogPost(int dogId, String handle) {

        model.addLikeToDog(dogId);
        model.addDogLike(dogId, handle);
        System.out.println("Dog liked.");
    }

    /**
     * Getter for all the dog likes
     * @return an ArrayList containing all the dog likes information
     */
    public ArrayList<String> getAllDogLikes() {
        return model.getDogLikes();
    }

    /**
     * A method calculation the total number of dog likes for a dog
     * @param dogId
     *          the dog the likes are calculated for
     * @return an integer representing the number of likes for the dog
     */
    public int getNumberOfDogLikes(int dogId) {

        ArrayList<Dog> dogs = model.getDogList();

        int likes = 0;

        for (Dog dog : dogs) {
            if (dog.getDogId() == dogId)
                likes += dog.getLikes();
        }
        return likes;
    }
}
