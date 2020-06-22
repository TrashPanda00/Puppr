package network;

import model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The RmiClient class establishes the communication with the server by looking up the server stub
 * and downloading it
 * The class calls the <code>exportObject</code> method in order to be able to send objects to the server.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class RmiClient implements LocalClientModel, RemoteListener<Object, Object>
{
  private RemoteModel remoteModel;
  private LocalModel localModel;
  private PropertyChangeAction<Object, Object> property;

  /**
   * A constructor looking up the name of the server stub in the registry and downloading it
   * in order to send and receive objects from the server.
   * @param localModel an instance of the LocalModel interface used to communicate with the
   *                   local model classes.
   */
  public RmiClient(LocalModel localModel)
  {
    try
    {
      this.localModel=localModel;
      this.remoteModel= (RemoteModel) Naming.lookup("rmi://localhost:1099/puppr");
      UnicastRemoteObject.exportObject(this,0);
      remoteModel.addListener(this);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    property=new PropertyChangeProxy<>(this,true);

  }

  /**
   * A method used to rearrange the exception message
   * @param e the exception that was thrown
   * @return the rearranged version of the exception message
   */
  private String getExceptionMessage(RemoteException e)
  {String message = e.getMessage();
    if (message != null)
      message = message.split(";")[0];
    return message;
  }

  /**
   * Setter for the user bio. The setter communicates the changes to the server by
   * calling a RemoteModel method.
   * @param user
   *        the user for who the bio was set
   */
  @Override
  public void setBio(User user) {
    try {
      remoteModel.setBio(user);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for adding a new comment to a post. The method communicates the
   * change to the server through the RemoteModel method call.
   * @param comment
   *        the new comment being added
   */
  @Override public void addComment(Comment comment)
  {
    try
    {
      remoteModel.addComment(comment);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used for editting an already existing comment.
   * The method notifies and communicates the changes to the server by calling
   * a RemoteModel method.
   * @param comment
   *        the edited version of the comment object
   */
  @Override public void editComment(Comment comment)
  {
    try
    {
      remoteModel.editComment(comment);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Method used to remove an already existing comment from a post.
   * The method notifies the server about the change through
   * the RemoteModel method.
   * @param comment
   *        the comment that is to be removed
   */
  @Override public void removeComment(Comment comment)
  {
    try
    {
      remoteModel.removeComment(comment);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for the full list of comments. The method gets the list
   * directly from the database by communicating with the server through
   * the RemoteModel interface when calling its method.
   * @return an ArrayList of Comment data type which contains the full list
   * of all the comments ever posted on all the posts from the Puppr app.
   */
  @Override public ArrayList<Comment> getCommentList()
  {
    try
    {
      return remoteModel.getCommentList();
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Getter for all the comments of a specific post. The method gets the list
   * directly from the database by communicating with the server through
   * the RemoteModel interface when calling its methods.
   * @param postId
   *        the post id of the post for which the comments will be retrieved
   * @return an ArrayList of Comment data type, encapsulating all the comments ever
   * posted on the specified post
   */
  @Override public ArrayList<Comment> getCommentForPost(int postId)
  {
    try
    {
      return remoteModel.getCommentForPost(postId);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * A method used to add a new dog to a user's profile. The method
   * communicates the change to the server by calling the RemoteModel method.
   * @param dog
   *        the new Dog object to be added.
   */
  @Override public void addDog(Dog dog)
  {
    try
    {
      remoteModel.addDog(dog);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to edit the information of an already existing dog. This method
   * communicates the proposed changes to the server by calling the RemoteModel method.
   * @param dog
   *        the Dog object that is to be modified
   */
  @Override public void editDog(Dog dog)
  {
    try
    {
      remoteModel.editDog(dog);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to remove an already existing dog from a user's profile.
   * The method communicates the change to the server by calling the RemoteModel method.
   * @param dog
   *        the Dog object that is to be removed
   */
  @Override public void removeDog(Dog dog)
  {
    try
    {
      remoteModel.removeDog(dog);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the dogs a user has linked to their profile. The method
   * calls a RemoteModel method, communicating with the server and getting the list
   * directly from the database.
   * @param handle
   *        the handle of the user for which the list of dogs is needed.
   *        Handle acts as the unique identifier for the User objects
   * @return an ArrayList of Dog data type which contains all the Dog object
   * the user has linked to their profile. The list can be <code>null</code>.
   */
  @Override public ArrayList<Dog> getDogsForUser(String handle)
  {
    try
    {
      return remoteModel.getDogsForUser(handle);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Getter for the complete list of all the dogs on the Puppr platform. The method
   * gets the information from the database by communicating with the server
   * through the RemoteModel interface.
   * @return an ArrayList of Dog data type, encapsulating all the Dog objects in the
   * Puppr database.
   */
  @Override public ArrayList<Dog> getDogList()
  {
    try
    {
      return remoteModel.getDogList();
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Method used to add a new post by a user. This method communicates
   * with the server by calling the RemoteModel method and send the
   * information about the new Post object.
   * @param post
   *        the new Post object that is to be added to the database.
   */
  @Override public void addPost(Post post)
  {
    try
    {
      remoteModel.addPost(post);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to edit an already existing post. The changes
   * are communicated to the server through the use of the
   * RemoteModel method.
   * @param post
   *        the Post object that is to be modified.
   */
  @Override public void editPost(Post post)
  {
    try
    {
      remoteModel.editPost(post);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to remove an already existing post. The method
   * notifies the server by using the RemoteModel method.
   * @param post
   *        the Post object that is to be deleted
   */
  @Override public void removePost(Post post)
  {
    try
    {
      remoteModel.removePost(post);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the posts made on the Puppr app.
   * The method gets the information from the database by communicating
   * to the server through the RemoteModel interface.
   * @return an ArrayList of Post data type, containing all the Post object
   * in the database.
   */
  @Override public ArrayList<Post> getPostList()
  {
    try
    {
      return remoteModel.getPostList();
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Getter for all the postes made by a specific user. The posts are retrieved
   * from the database by communicating with the server through the use of the
   * RemoteModel method.
   * @param handle
   *        the handle of the user for which the list of the posts is needed
   * @return an ArrayList of Post data type which contains all the Post objects ever
   * made by the specified user
   */
  @Override public ArrayList<Post> getPostsForUser(String handle)
  {
    try
    {
      return remoteModel.getPostsForUser(handle);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Getter for a specific post. The data about the Post object is retrieved
   * from the database by communicating with the server throught the RemoteModel
   * interface and using one of its methods.
   * @param postId
   *        the unique id specific to each post, used to differentiate between posts
   * @return a Post object with the same value for the <code>postId</code> attribute as
   * the specified parameter value.
   */
  @Override public Post getPostById(int postId)
  {
    try
    {
      return remoteModel.getPostById(postId);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * A method user to add a new user after sign up. The method
   * sends the information to the server with the use of the
   * RemoteModel method.
   * @param user
   *        the new User object that is to be added.
   */
  @Override public void addUser(User user)
  {
    try
    {
      remoteModel.addUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to remove an already existing user. The method
   * notifies the server by communicating with it using the
   * RemoteModel method.
   * @param user
   *        the User object that is to be removed
   */
  @Override public void removeUser(User user)
  {
    try
    {
      remoteModel.removeUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to edit an already existing user. The method sends the
   * information to the server by communicating with it using the RemoteModel
   * interface and its methods.
   * @param user
   *        the User object that is to be modified.
   */
  @Override public void editUser(User user)
  {
    try
    {
      remoteModel.editUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for a list of all the users of the Puppr app. The method
   * returns all the user information from the database by communicating with
   * the server through the RemoteModel interface.
   * @return an ArrayList of User data type containing all the users of the Puppr app
   */
  @Override public ArrayList<User> getUserList()
  {
    try
    {
      return remoteModel.getUserList();
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Getter for a user based on their handle. The user information
   * is retrieved from the database by communicating with the
   * server through the RemoteModel interface.
   * @param handle
   *        the unique handle used to identify a user from another
   * @return an User object with the same handle value as the specified parameter value
   */
  @Override public User getUserByHandle(String handle)
  {
    try
    {
      return remoteModel.getUserByHandle(handle);
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * A method used to add a like to a comment. The new like information
   * is sent to the server using the RemoteModel method.
   * @param commentId
   *        the id of the comment that has been liked
   */
  @Override public void addLikeToComment(int commentId)
  {
    try
    {
      remoteModel.addLikeToComment(commentId);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for liking a post. The like information is sent to the
   * server using the RemoteModel method.
   * @param postId
   *        the id of the post that has been liked
   */
  @Override public void addLikeToPost(int postId)
  {
    try
    {
      remoteModel.addLikeToPost(postId);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for liking a dog. The like information is sent to the
   * server using the RemoteModel method.
   * @param dogId
   *        the id of the dog that has been liked
   */
  @Override public void addLikeToDog(int dogId)
  {
    try
    {
      remoteModel.addLikeToDog(dogId);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }


  /**
   * A method for adding a like to a post and linking the user's handle.
   * The like information is sent to the server using the RemoteModel method.
   * @param postId
   *        the id of the post the like was added on
   * @param handle
   *        the handle of the user who liked the post
   */
  @Override
  public void addLike(int postId, String handle) {
    try {
      remoteModel.addLike(postId,handle);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the likes added of posts. The information
   * is retrieved directly form the server using the RemoteModel
   * method.
   * @return an ArrayList of String data type containing all
   * the postId - handle pairs for the likes.
   * Example: "12 - dogOwner1".
   */
  @Override
  public ArrayList<String> getLikes() {
    try {
      return remoteModel.getLikes();
    } catch (RemoteException e) {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * Method for adding a like to a dog and linking the user's handle to the like.
   * @param dogId
   *        the id of the Dog object that the like was added on
   * @param handle
   *        the handle of the user who liked the dog
   */
  @Override public void addDogLike(int dogId, String handle)
  {
    try
    {
      remoteModel.addDogLike(dogId, handle);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the dog likes. The information is retrieved
   * directly from the database by communicating with the server through the
   * RemoteModel interface
   * @return an ArrayList of String data type containing all the
   * dogId - handle pairs for the likes.
   * Example: "1 - dogOwner232"
   */
  @Override public ArrayList<String> getDogLikes()
  {
    try
    {
      return remoteModel.getDogLikes();
    }
    catch (RemoteException e)
    {
      throw new IllegalStateException(getExceptionMessage(e), e);
    }
  }

  /**
   * A method for adding a new like to a comment and saving the commentId - handle
   * information in the database.
   * @param commentId
   *        the id of the comment that was added
   * @param handle
   *        the handle of the user who made the comment
   */
  @Override
  public void addCommentLike(int commentId, String handle) {
    try
    {
      remoteModel.addCommentLike(commentId,handle);
    }
    catch(RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for a list of all the comment likes
   * @return an ArrayList of String data type containing all the pairs in the
   * for of commentId - handle.
   * Example: "13 - sunny".
   */
  @Override
  public ArrayList<String> getCommentLikes() {
    try
    {
      return remoteModel.getCommentLikes();
    }
    catch(RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Method used to close the connection between the client and the server
   * by calling the <code>unexportObject</code> method.
   */
  @Override public void close()
  {
    try
    {
      UnicastRemoteObject.unexportObject(this,true);
    }
    catch (NoSuchObjectException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method that is called whenever a new property change is received by the listeners.
   * @param event
   *        the new event that was heard by the listeners
   */
  @Override public void propertyChange(ObserverEvent<Object, Object> event)

  {
    property.firePropertyChange(event);
  }

  /**
   * A method for adding a new listener to the current class.
   * @param listener
   *        the listener that is being added
   * @param propertyNames
   *        the list of name the listener is listening for
   * @return a boolean value representing if the listener was added or not
   */
  @Override public boolean addListener(GeneralListener<Object, Object> listener,
                                       String... propertyNames)
  {
    return property.addListener(listener, propertyNames);
  }

  /**
   * A method for removing a listener from the current class.
   * @param listener
   *        the listener that is being removed
   * @param propertyNames
   *        the list of names for which the listener is being removed
   * @return a boolean value representing the successful removal of
   * the listener.
   */
  @Override public boolean removeListener(GeneralListener<Object, Object> listener,
                                          String... propertyNames)
  {
    return property.removeListener(listener, propertyNames);
  }


}
