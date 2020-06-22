package viewmodel;

import model.Comment;
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
 * The ViewPostViewModel class handles the logic behind the viewPost view
 *
 * @author Natali-Munk Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class ViewPostViewModel implements LocalListener<Object,Object>, LocalSubject<Object,Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private ProfileViewModel profileViewModel;
    private boolean fromHome;
    private Post currentPost;
    private String currentUserHandle;
    private boolean isGuest;
    private PropertyChangeAction<Object,Object> property;

    /**
     * A constructor setting the local variables and listeners
     * @param model
     *          an instance of the LocalModel interface
     * @param homeViewModel
     *          an instance of the HomeViewModel class
     * @param profileViewModel
     *          an instance of the ProfileViewModel class
     */
    public ViewPostViewModel(LocalModel model, HomeViewModel homeViewModel, ProfileViewModel profileViewModel){
        this.model=model;
        this.homeViewModel=homeViewModel;
        this.homeViewModel.addListener(this);
        isGuest=homeViewModel.isGuest();
        this.profileViewModel=profileViewModel;
        this.profileViewModel.addListener(this);
        property = new PropertyChangeProxy<>(this);
    }

    /**
     * A method checking is the user entered the view from the home view
     * @return a boolean value equal to true if the user entered this view from the home view
     */
    public boolean isFromHome(){
        return fromHome;
    }

    /**
     * Getter for a post object based on its id
     * @param id
     *          the id of the post whose information is needed
     * @return a Post object
     */
    public Post getPostById(int id){
        return model.getPostById(id);
    }

    /**
     * Getter for a user object based on its handle
     * @param handle
     *          the handle of the user whose information is needed
     * @return an User object
     */
    public User getUserByHandle(String handle){
        return model.getUserByHandle(handle);
    }

    /**
     * Setter for the comment of a post
     * @param post
     *          the post to which the comment was added
     */
    public void setComment(Post post) {
        property.firePropertyChange("commentView", currentUserHandle, post);
    }

    /**
     * Getter for all the post likes
     * @return an ArrayList containing all the information related to post likes
     */
    public ArrayList<String> getAllLikes(){
        return homeViewModel.getAllLikes();
    }

    /**
     * Getter for all the comment likes
     * @return an ArrayList containing all the information related to comment likes
     */
    public ArrayList<String> getAllCommentLikes(){
        return homeViewModel.getAllCommentLikes();
    }

    /**
     * A method for liking a comment
     * @param commentId
     *          the id of the comment that was liked
     * @param handle
     *          the handle of the user who liked the comment
     */
    public void likeComment(int commentId, String handle) {
        homeViewModel.likeComment(commentId,handle);
    }

    /**
     * Getter for all the comments of a post
     * @param postId
     *          the id of the post whose comments are needed
     * @return an ArrayList of comment data type containing all the comments
     * related to the specified post
     */
    public ArrayList<Comment> getCommentForPost(int postId){ return model.getCommentForPost(postId);}

    /**
     * Getter for the current user's handle
     * @return a reference to the handle value
     */
    public String getCurrentUserHandle() { return currentUserHandle; }

    /**
     * A method for editing a comment
     * @param comment
     *          the modified comment object
     */
    public void editComment(Comment comment){ property.firePropertyChange("editComment", null, comment);}

    /**
     * A method for deleting a comment
     * @param comment
     *          the comment that is to be deleted
     */
    public void deleteComment(Comment comment){
        model.removeComment(comment);
    }

    /**
     * A method for opening a user's profile
     * @param handle
     *          the handle of the user whose profile page will be opened
     */
    public void openUserProfile(String handle) {
        homeViewModel.openUserProfile(handle);
    }

    /**
     * A method checking if the current user is a guest
     * @return true is the user is a guest and false otherwise
     */
    public boolean isGuest() {
        return isGuest;
    }

    /**
     * A method for liking a post
     * @param postId
     *          the id of the post that was liked
     * @param handle
     *          the handle of the user who liked the post
     */
    public void likePost(int postId, String handle) {
        homeViewModel.likePost(postId,handle);
    }

    /**
     * Getter for the post that is viewed
     * @return a reference to the Post object
     */
    public Post getCurrentPost(){
        return currentPost;
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
     * A method being called whenever a subject property changes and fires an event. Method acts differently
     * depending on the property name
     * @param event
     *        the event that was fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        if(event.getPropertyName().equals("viewPostFromHome")){
            fromHome=true;
            property.firePropertyChange("viewPost",event.getValue1(),event.getValue2());
            currentPost=getPostById((int)event.getValue2());
            currentUserHandle=event.getValue1()+"";

        }
        else if(event.getPropertyName().equals("viewPostFromProfile")){
            fromHome=false;
            property.firePropertyChange("viewPost",event.getValue1(),event.getValue2());
            currentPost=getPostById((int)event.getValue2());
            currentUserHandle=event.getValue1()+"";

        }
        if(event.getPropertyName().equals("guest")){
            isGuest= (boolean) event.getValue2();
            property.firePropertyChange("guest",null, event.getValue2());
        }

        if(event.getPropertyName().equals("user")){
            isGuest=false;
            property.firePropertyChange("user",null, false);
        }

    }
}
