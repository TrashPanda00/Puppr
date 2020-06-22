package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Comment;
import model.LocalModel;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;

/**
 * The EditCommentViewModel class handles the functionality and logic behind the editComment view
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class EditCommentViewModel implements LocalListener<Object, Object> {
    private LocalModel model;
    private ViewPostViewModel viewPostViewModel;
    @SuppressWarnings("CanBeFinal")
    private StringProperty comment;
    private Comment commentObject;
    private StringProperty handle;

    /**
     * A constructor setting up the class variables and the listeners
     * @param model
     *          an instance of the LocalModel interface
     * @param viewPostViewModel
     *          an instance of the ViewPostViewModel class
     */
    public EditCommentViewModel(LocalModel model, ViewPostViewModel viewPostViewModel) {
        this.model = model;
        this.viewPostViewModel = viewPostViewModel;
        this.viewPostViewModel.addListener(this, "editComment");
        this.comment = new SimpleStringProperty();
        this.handle = new SimpleStringProperty();
    }

    /**
     * Getter for the comment property
     * @return a reference to the comment String property
     */
    public StringProperty commentProperty() {
        return comment;
    }

    /**
     * A method for adding the edited comment
     * @param comment
     *          the modified comment object that will be sent to the LocalModel
     *          and then saved in the database
     */
    public void addComment(Comment comment) {
        model.addComment(comment);
    }

    /**
     * Getter for the current user's handle
     * @return a reference to the user's handle
     */
    public String getHandle() {
        return handle.get();
    }

    /**
     * Getter for the unedited comment
     * @return a reference to the unedited comment object
     */
    public Comment getCommentObject() {
        return commentObject;
    }

    /**
     * A method for editing the comment and sending the new information to the LocalModel
     */
    public void editComment() {
        Comment oldComment = commentObject;
        Comment newComment = new Comment(oldComment.getCommentId(), comment.get(), oldComment.getHandle(), oldComment.getLikes(), oldComment.getTimePosted(), oldComment.getPostId());
        model.editComment(newComment);
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
                case "editComment":
                    commentObject = (Comment) event.getValue2();
                    comment.set(commentObject.getBody());
                    break;
            }
        });
    }
}
