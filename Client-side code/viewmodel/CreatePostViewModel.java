package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.Post;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.time.LocalDateTime;

/**
 * The CreatePostViewModel class handles the functionality and the logic behind the createPost view.
 * The class sends any new post information to the LocalModel from where it will be sent to the server
 * and saved as information in the database.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class CreatePostViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private StringProperty post;
    private StringProperty name;
    private String changeableHandle;
    private byte[] imageUrl;

    /**
     * A constructor initialising the class variables and adding the listeners
     * @param model
     *          an instance of the LocalModel class
     * @param homeViewModel
     *          an instance of the HomeViewModel class
     */
    public CreatePostViewModel(LocalModel model, HomeViewModel homeViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        homeViewModel.addListener(this, "user");
        this.post = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    /**
     * Setter for the post image
     *
     * @param imageUrl the url for the post picture
     */
    public void setImage(byte[] imageUrl){
        this.imageUrl=imageUrl;
    }


    /**
     * A method for adding a new post. The method sends the information further to the LocalModel
     * and updates the posts in the home view
     *          the new post object that was added
     */
    public void addPost(){
        Post post1 = new Post(imageUrl, changeableHandle, 0, LocalDateTime.now(), post.get(), null);
        model.addPost(post1);
        homeViewModel.updatePosts();
    }


    /**
     * Getter for the new post that was added
     * @return a reference to the current post object
     */
    public String getPost() {
        return post.get();
    }

    /**
     * Getter for the post property
     * @return a reference to the post String property
     */
    public StringProperty postProperty() {
        return post;
    }

    /**
     * Getter for the changeable handle
     * @return a reference to the <code>changeableHandle</code> value
     */
    public String getChangeableHandle() {
        return changeableHandle;
    }

    /**
     * Getter for the user's name
     * @return a reference to the current user's name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Getter for the user's name property
     * @return a reference to the name String property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * A method being called whenever the subject property changes and fires an event
     * @param event
     *          the event fired by the property change and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        Platform.runLater(() -> {
            switch ((event.getPropertyName())) {
                case "user":
                    changeableHandle = event.getValue2().toString();
                    name.set(event.getValue1().toString());
                    break;
            }
        });
    }

}
