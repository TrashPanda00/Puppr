package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.Post;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

/**
 * The EditPostViewModel class handles the logic behind the editPost view
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class EditPostViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private HomeViewModel homeViewModel;
    private ProfileViewModel profileViewModel;
    private StringProperty post;
    private byte[] imageUrl;
    private Post postObject;
    private int postId;
    private boolean fromHome;

    /**
     * A  constructor setting the local variables and adding the listeners
     * @param model
     *          an instance of the LocalModel interface
     * @param homeViewModel
     *          an instance of the HomeViewModel class
     * @param profileViewModel
     *          an instance of the ProfileViewModel class
     */
    public EditPostViewModel(LocalModel model, HomeViewModel homeViewModel, ProfileViewModel profileViewModel) {
        this.model = model;
        this.homeViewModel = homeViewModel;
        this.profileViewModel = profileViewModel;
        this.homeViewModel.addListener(this, "editPost");
        this.profileViewModel.addListener(this, "editPost");
        this.post = new SimpleStringProperty();
        imageUrl = null;

    }

    /**
     * Getter fot the post property
     * @return a reference to the post String property
     */
    public StringProperty postProperty() {
        return post;
    }

    /**
     * Getter for a post based on its id
     * @return a reference to the Post object with the specified id value
     */
    public Post getPostById() {
        return model.getPostById(postId);
    }

    /**
     * A method for editing a post object and sending the information to the
     * LocalModel
     */
    public void editPost() {
        Post oldPost = postObject;
        Post newPost = new Post(oldPost.getPostId(), imageUrl, oldPost.getHandle(), oldPost.getLikes(),
                                oldPost.getTimePosted(), post.get(), oldPost.getComments());
        model.editPost(newPost);
        homeViewModel.updatePosts();

    }

    public boolean isFromHome() {
        return fromHome;
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
                case "editPost":
                    fromHome = (boolean)event.getValue1();
                    postObject = (Post) event.getValue2();
                    post.set(((Post) event.getValue2()).getText());
                    imageUrl = ((Post) event.getValue2()).getImageURL();
                    postId = ((Post) event.getValue2()).getPostId();
                    break;
            }
        });
    }
}