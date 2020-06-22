package model;

import network.LocalClientModel;
import network.RmiClient;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.util.ArrayList;

/**
 * The LocalModelManager is a class which implements the LocalModel interface, overrides and gives functionality to all of its methods.
 *
 * @author Natali-Munk Jakobsen
 * @version 1.0
 */
public class LocalModelManager implements LocalModel
{
  private LocalClientModel localClientModel;
  private PropertyChangeAction<Object, Object> property;

  /**
   * A constructor setting the values for the LocalClientModel and PropertyChangeAction
   * instances and adding a local listener for all the events in the LocalClientModel interface
   */
  public LocalModelManager()
  {
    this.localClientModel =new RmiClient(this);
  }

  /**
   * A setter for the user bio calling the LocalClientModel method
   * @param user
   *            the user for who the bio was modified or set
   */
  @Override
  public void setBio(User user) {
    localClientModel.setBio(user);
  }

  /**
   * A method for adding a new comment to a post, calling the LocalClientModel method
   * @param comment
   *        the comment that was added to the post
   */
  @Override public void addComment(Comment comment)
  {
    localClientModel.addComment(comment);
  }

  /**
   * A method for editing a comment which was already posted. The method calls the LocalClientModel
   * version of the same method in order to send the information to the server.
   * @param comment
   *        the comment on which the modification was made on
   */
  @Override public void editComment(Comment comment)
  {
    localClientModel.editComment(comment);
  }

  /**
   * A method for removing a comment. The method calls the LocalClientModel
   * version of the same method in order to send the information to the server.
   * @param comment
   *        the comment which was deleted from the post
   */
  @Override public void removeComment(Comment comment)
  {
    localClientModel.removeComment(comment);
  }

  /**
   * A method for getting the list of comments for all post. The method calls the LocalClientModel
   * version of the same method in order to get the information from the server.
   * @return an ArrayList with Comment as data type, representing all the comments for all the posts on the Puppr platform
   */
  @Override public ArrayList<Comment> getCommentList()
  {
    return localClientModel.getCommentList();
  }

  /**
   * A method for getting the list of comment for a particular post. The method calls the LocalClientModel
   * version of the same method in order to get the information from the server.
   * @param postId
   *        the post id of the post for which the comments will be retrieved
   * @return an ArrayList with Comment as the data type, which contains all the comments for the post specified by the postId
   */
  @Override public ArrayList<Comment> getCommentForPost(int postId) {
    return localClientModel.getCommentForPost(postId);
  }

  /**
   * A method for linking a new Dog to a user profile. The method calls the LocalClientModel
   * version of the same method in order to send the information to the server.
   * @param dog
   *        the new Dog object that was added
   */
  @Override public void addDog(Dog dog)
  {
    localClientModel.addDog(dog);
  }

  /**
   * A method for editing a Dog object. The method calls the LocalClientModel
   * version of the same method in order to send the information to the server.
   * @param dog
   *        the dog object that was modified
   */
  @Override public void editDog(Dog dog)
  {
    localClientModel.editDog(dog);
  }

  /**
   * A method for removing a Dog object linked to a user profile. The method calls the
   * LocalClientModel version of the same method in order to send the information to the server.
   * @param dog
   *        the dog that is to be removed from a user profile
   */
  @Override public void removeDog(Dog dog)
  {
    localClientModel.removeDog(dog);
  }

  /**
   * A method for getting all the Dog objects linked to a user. The method calls
   * the LocalClientModel version of the same method in order to get the information from the server.
   * @param handle
   *        the handle of the user for which the list of dogs is needed.
   *        Handle acts as the unique identifier for the User objects
   * @return an ArrayList of Dog data type containing all the Dog objects related to the User described the the given handle parameter.
   */
  @Override public ArrayList<Dog> getDogsForUser(String handle)
  {
    return localClientModel.getDogsForUser(handle);
  }

  /**
   * A method for getting the complete list of all the dogs in the Puppr database
   * The method calls the LocalClientModel version of the same method in
   * order to get the information from the server.
   * @return an ArrayList of Dog data type encapsulating all the Dog objects in the database
   */
  @Override public ArrayList<Dog> getDogList()
  {
    return localClientModel.getDogList();
  }

  /**
   * A method for adding a new post calling the LocalClientModel version of the same method
   * in order to send the information to the server.
   * @param post
   *        the new post to be added
   */
  @Override public void addPost(Post post)
  {
    localClientModel.addPost(post);
  }

  /**
   * A method for editing a post calling the LocalClientModel version of the same method
   * in order to send the information to the server.
   * @param post
   *        the post object that was edited
   */
  @Override public void editPost(Post post)
  {
    localClientModel.editPost(post);
  }

  /**
   * A method for removing an already existing post. The method calls the LocalClientModel
   * version of the same named method in order to send the information to the server.
   * @param post
   *        the post that is to be removed
   */
  @Override public void removePost(Post post)
  {
    localClientModel.removePost(post);
  }

  /**
   * A method for getting a list of all the post ever added. The method calls the LocalClientModel
   * version of the same named method in order to get the information from the server.
   * @return an ArrayList of Post data type containing all the posts that were ever added by all the different users
   */
  @Override public ArrayList<Post> getPostList()
  {
    return localClientModel.getPostList();
  }

  /**
   * A method for getting a list of all the posts for a specific user. The method calls the LocalClientModel
   * version of the same named method in order to get the information from the server.
   * @param handle
   *        the handle of the user for which the list of the posts is needed
   * @return an ArrayList of Post data type containing all the posts ever added by the specified user
   */
  @Override public ArrayList<Post> getPostsForUser(String handle)
  {
    return localClientModel.getPostsForUser(handle);
  }

  /**
   * A method used to retrieve a singular post based on its id. The method calls the LocalClientModel
   * version of the same named method in order to get the information from the server.
   * @param postId
   *        the unique id specific to each post, used to differentiate between posts
   * @return the post which has the specified <code>postId</code>
   */
  @Override public Post getPostById(int postId)
  {
    return localClientModel.getPostById(postId);
  }

  /**
   * A method for adding a new user, signaling a new user completed the sign up process and sending the information
   * further to the server by calling the <code>addUser</code> method in LocalClientModel interface.
   * @param user
   *        the new User object to be added, containing all the information completed by the person on sign up
   */
  @Override public void addUser(User user)
  {
    localClientModel.addUser(user);
  }

  /**
   * A method used to remove an already existing user. The method calls the LocalClientModel
   * version of the <code>removeUser</code> method in order to send the information to the server.
   * @param user
   *        the user that will be removed
   */
  @Override public void removeUser(User user)
  {
    localClientModel.removeUser(user);
  }

  /**
   * A method for editing an user which sends the information further to the server by
   * calling the same <code>editUser</code> method in LocalClientModel interface.
   * @param user
   *        the user that was modified
   */
  @Override public void editUser(User user)
  {
    localClientModel.editUser(user);
  }

  /**
   * A method used to get the complete list of all the user of the Puppr app from the server by calling
   * the <code>getUserList</code> method from the LocalClientModel interface.
   * @return an ArrayList of User data type which contains all the user in the Puppr database
   */
  @Override public ArrayList<User> getUserList()
  {
    return localClientModel.getUserList();
  }

  /**
   * A method for getting a user based on the user handle from the server by calling the
   * version of the <code>getUserByHandle</code> method from the LocalClientModel interface.
   * @param handle
   *        the unique handle used to identify a user from another
   * @return an User object with the handle attribute equal to the handle parameter given
   */
  @Override public User getUserByHandle(String handle)
  {
    return localClientModel.getUserByHandle(handle);
  }

  /**
   * A method for adding a like to a comment and sending the information to the server by calling
   * the LocalClientModel method.
   * @param commentId
   *        the comment id of the Comment object on which the like was added
   */
  @Override public void addLikeToComment(int commentId)
  {
    localClientModel.addLikeToComment(commentId);
  }

  /**
   * A method for adding a like to a post and sending the information further to the server
   * by calling the LocalClientModel method.
   * @param postId
   *        the unique post id of the Post object on which the like was added
   */
  @Override public void addLikeToPost(int postId)
  {
    localClientModel.addLikeToPost(postId);
  }

  /**
   * A method used to add a like to a Dog object and also sending the information further to the server
   * by calling the LocalClientModel method.
   * @param dogId
   *        the unique dog id of the dog on which the like was added
   */
  @Override public void addLikeToDog(int dogId)
  {
    localClientModel.addLikeToDog(dogId);
  }

  /**
   * A method used to add a liked to a post and save the user info of the user who liked the post.
   * This information is to be sent to the server by calling the LocalClientModel.
   * @param postId
   *        the id of the post the like was added on
   * @param handle
   *        the handle of the user who liked the specified post
   */
  @Override public void addLike(int postId, String handle) {
    localClientModel.addLike(postId,handle);
  }

  /**
   * A method for getting all the postId and handle info for all the likes added and requesting the
   * information from the server through the LocalClientModel.
   * @return an ArrayList of String data type containing all the postId and handle pairs for every like
   *         on all the posts ever added
   */
  @Override public ArrayList<String> getLikes() {
    return localClientModel.getLikes();
  }

  /**
   * A method used to add a like to a Dog object and save the user info of the user who liked the dog.
   * The method calls the <code>addDogLike</code> method of the LocalClientModel interface in order to
   * send the information to the server.
   * @param dogId
   *        the id of the Dog object that the like was added on
   * @param handle
   *        the handle of the user that liked the specified dog
   */
  @Override public void addDogLike(int dogId, String handle)
  {
    localClientModel.addDogLike(dogId, handle);
  }

  /**
   * A method for getting a list of all the dogId and handle info of all the likes for dogs from the server.
   * @return the dogId and handle pairs for each like added on all the Dog objects that exist in the database
   */
  @Override public ArrayList<String> getDogLikes()
  {
    return localClientModel.getDogLikes();
  }

  /**
   * A method for adding a new like to a comment and saving the commentId - handle
   * information in the database.
   * @param commentId
   *        the id of the comment that was added
   * @param handle
   *        the handle of the user who liked the comment
   */
  @Override
  public void addCommentLike(int commentId, String handle) {
    localClientModel.addCommentLike(commentId, handle);
  }

  /**
   * Getter for a list of all the comment likes
   * @return an ArrayList of String data type containing all the pairs in the
   * form of commentId - handle.
   * Example: "13 - sunny".
   */
  @Override
  public ArrayList<String> getCommentLikes() {
    return localClientModel.getCommentLikes();
  }

  /**
   * A method used to close the connection between the client and the server side of the app
   */
  @Override public void close()
  {
    localClientModel.close();
  }


}
