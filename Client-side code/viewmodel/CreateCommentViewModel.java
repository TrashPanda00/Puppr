package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Comment;
import model.LocalModel;
import model.Post;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

/**
 * The CreateCommentViewModel class handles the functionality and login behind the createComment view.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class CreateCommentViewModel implements LocalListener<Object, Object>, LocalSubject<Object,Object>
{
  private LocalModel model;
  private HomeViewModel homeViewModel;
  private ViewPostViewModel viewPostViewModel;
  private StringProperty comment;
  private String handle;
  private Post post;
  private PropertyChangeAction<Object,Object> property;

  /**
   * A constructor setting the local variables and the listeners for HomeViewModel and ViewPostViewModel events
   * @param model
   *          an instance of the LocalModel interface
   * @param homeViewModel
   *          an instance of the HomeViewModel class
   * @param viewPostViewModel
   *          an instance of the ViewPostViewModel class
   */
  public CreateCommentViewModel(LocalModel model, HomeViewModel homeViewModel, ViewPostViewModel viewPostViewModel)
  {
    this.model = model;
    this.homeViewModel = homeViewModel;
    this.homeViewModel.addListener(this,"comment");
    this.viewPostViewModel=viewPostViewModel;
    this.viewPostViewModel.addListener(this,"commentView");
    this.comment=new SimpleStringProperty();
    property = new PropertyChangeProxy<>(this);
  }

  /**
   * Getter for the comment property
   * @return a reference to the comment String property
   */
  public StringProperty commentProperty()
  {
    return comment;
  }

  /**
   * A method used for adding a new comment to the post
   *        the new comment object that will be sent to the model and then saved
   *        in the database
   */
  public void addComment(){
    Comment commentObject = new Comment(comment.get(), handle, post.getPostId());
    model.addComment(commentObject);
  }

  /**
   * A method for setting the post
   * @param post
   *        the Post object on which the comment will be added
   */
  public void setPost(Post post)
  {
    this.post = post;
  }

  /**
   * Getter for the current post
   * @return a reference to the Post object on which the comment will be added to
   */
  public Post getPost()
  {
    return post;
  }

  /**
   * A method being called whenever a subject property changes and fires an event
   * @param event
   *        the event that was fired by the property change and listened for here
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> event)
  {
    Platform.runLater(() -> {
      switch ((event.getPropertyName())){
        case "comment":
          setPost((Post) event.getValue2());
          handle=event.getValue1().toString();
          property.firePropertyChange("comment", event.getValue1(), true);
          break;
        case "commentView":
          setPost((Post) event.getValue2());
          handle=event.getValue1().toString();
          property.firePropertyChange("comment", event.getValue1(), false);
          break;
      }
    });
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
    return property.addListener(listener,propertyNames);
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
    return property.removeListener(listener,propertyNames);
  }
}
