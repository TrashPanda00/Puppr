package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;


import java.util.ArrayList;

/**
 * The HomeViewModel class handles the logic behind the home view. It assures that the main post feed is
 * always up tp date and that all the changes and received and sent where needed.
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class HomeViewModel implements LocalListener<Object, Object>, LocalSubject<Object, Object> {
    private LocalModel model;
    private PropertyChangeAction<Object, Object> property;
    private StringProperty handle;
    private StringProperty name;
    private LoginViewModel loginViewModel;
    private String handleString;
    private boolean isGuest;

    /**
     * A constructor setting up the local variables and the listeners
     * @param model
     *          an instance of the LocalModel interface
     * @param loginViewModel
     *          an instance of the LoginViewModel class
     */
    public HomeViewModel(LocalModel model, LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        property = new PropertyChangeProxy<>(this);
        this.model = model;
        this.loginViewModel.addListener(this, "user");
        this.loginViewModel.addListener(this, "guest");
        handle = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }

    /**
     * A method being called whenever the subject property changes and fires an event. The class
     * acts differently depending on the property that changed
     * @param event
     *          the event fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {

        Platform.runLater(() -> {
            switch ((event.getPropertyName())) {
                case "user":
                    isGuest=false;
                    handleString = event.getValue2().toString();
                    handle.set(" @" + event.getValue2().toString());
                    name.set(getUserByHandle(event.getValue2().toString()).getName() + " " +
                            getUserByHandle(event.getValue2().toString()).getLastname());
                    property.firePropertyChange("user", name.get(), event.getValue2());
                    if (model.getUserByHandle(handleString).getUserType().equals("admin")) {
                        property.firePropertyChange("admin", null, true);
                    } else {
                        property.firePropertyChange("admin", null, false);
                    }
                    break;

                case "guest":
                    isGuest= (boolean) event.getValue2();
                    handleString=event.getValue1()+"";
                    name.set("Guest");
                    property.firePropertyChange("guest",null, event.getValue2());
                    break;
            }
        });
    }

    /**
     * A method used for updating a user's info and notifying the other classes of the change
     * @param newHandle
     *          the possibly new handle of the modified User object
     */
    public void updateUserInfo(String newHandle) {

        handleString = newHandle;
        User user = getUserByHandle(handleString);
        handle.set(" @" + handleString);
        name.set(getUserByHandle(handleString).getName() + " " +
                getUserByHandle(handleString).getLastname());

        ArrayList<Post> posts = model.getPostsForUser(handleString);

        for (Post post : posts) {
            Post newPost = new Post(post.getPostId(), post.getImageURL(),
                    handleString, post.getLikes(), post.getTimePosted(), post.getText(), post.getComments());
            model.editPost(newPost);
        }
        property.firePropertyChange("user", name, handleString);
        property.firePropertyChange("post", null, "change");
        property.firePropertyChange("bio", null, user.getBio());
    }

    /**
     * Getter for the total number of comments
     * @return an integer representing the total number of comments
     */
    public int numberOfComments() {
        return model.getCommentList().size();
    }

    /**
     * A method for liking a post and sending the information to the local model
     * @param postId
     *          the id of the post that was likes
     * @param handle
     *          the hadnle of the user who liked the post
     */
    public void likePost(int postId, String handle) {
        model.addLikeToPost(postId);
        model.addLike(postId, handle);
    }

    /**
     * A method for adding a comment to a post and sending the information to the
     * local model
     * @param comment
     *          the new comment object that was added
     */
    public void commentOnPost(Comment comment) {
        model.addComment(comment);
    }

    /**
     * Getter for the list of all the posts
     * @return an ArrayList of Post data type containing all the posts
     */
    public ArrayList<Post> getPostList() {
        return model.getPostList();
    }

    /**
     * Getter for the handle of the current user
     * @return a refernece to the current user's handle
     */
    public String getHandleString() {
        return handleString;
    }

    /**
     * Getter for a list of all the post likes
     * @return an ArrayList of String data type containing all the post
     * likes information
     */
    public ArrayList<String> getAllLikes() {
        return model.getLikes();
    }

    /**
     * Getter for all list of all the comment likes
     * @return an ArrayList of String data type containing all the comment
     * likes information
     */
    public ArrayList<String> getAllCommentLikes() {
        return model.getCommentLikes();
    }

    /**
     * A method for liking a comment and sending the information to the local model
     * @param commentId
     *          the id of the comment that was likes
     * @param handle
     *          the handle of the user who liked the comment
     */
    public void likeComment(int commentId, String handle) {
        model.addLikeToComment(commentId);
        model.addCommentLike(commentId,handle);
    }

    /**
     * A method checking if the current user is a guest type user
     * @return a boolean value equal to true is the user is a guest
     * or false otherwise
     */
    public boolean isGuest() {
        return isGuest;
    }

    /**
     * Getter for a user object based on its handle
     * @param handle
     *          the handle of the user object whose information is needed
     * @return a User object with the same value for handle as the specified one
     */
    public User getUserByHandle(String handle) {
        return model.getUserByHandle(handle);
    }

    /**
     * A method for deleting an existing post and sending the
     * information to the LocalModel
     * @param post
     *          the post that is to be deleted
     */
    public void deletePost(Post post) {
        model.removePost(post);
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
    public boolean addListener(GeneralListener<Object, Object> listener, String... propertyNames) {
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
    public boolean removeListener(GeneralListener<Object, Object> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }

    /**
     * Getter for the handle property
     * @return a reference to the handle String property
     */
    public StringProperty handleProperty() {
        return handle;
    }

    /**
     * Getter for the user's name property
     * @return a reference to the user's name String property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * A method used to set the profile picture of the local user and notify the other classes
     */
    public void setPortrait() {
        property.firePropertyChange("user", name.get(), handleString);
    }

    /**
     * A method used to add a comment and notify the other classes
     * @param post
     *          the post the comment was added on
     */
    public void setComment(Post post) {
        property.firePropertyChange("comment", handleString, post);
    }

    /**
     * A method used to update the current post list and notify the other classes
     */
   public void updatePosts() {
        property.firePropertyChange("post", null, "change");
    }

    /**
     * A method used to update the dog list and notify the other classes
     */
    public void updateDogs() {
        property.firePropertyChange("dog", null, "changeDogs");
    }

    /**
     * A method used to open the profile page of another user
     * @param handle
     *          the handle of the user whose profile page will be opened
     */
    public void openUserProfile(String handle) {

        property.firePropertyChange("otherUser", handleString, handle);
        if(handleString.equals(handle)) {
            User user = getUserByHandle(handle);
            property.firePropertyChange("user", user.getName()+" "+user.getLastname(), handle);
        }
    }

    /**
     * A method notifying the other classes that another user's profile page was opened
     */
    public void openProfile() {
        property.firePropertyChange("user", name.get(), handleString);
    }

    /**
     * A method notifying the other classes that a post was edited
     * @param post
     *          the Post object that was modified
     */
    public void editPost(Post post) {
        property.firePropertyChange("editPost", true, post);
    }

    /**
     * A method notifying the other classes that a post view was opened
     * @param postId
     *          the id of the post whose full view was opened
     */
    public void openPost(int postId){
        property.firePropertyChange("viewPostFromHome",handleString,postId);
    }

}
