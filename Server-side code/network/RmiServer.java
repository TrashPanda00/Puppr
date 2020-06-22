package network;

import model.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The RmiServer class is the server implementation of the RemoteModel interface, the interface that connects client side with server side.
 * This class creates and uploads the server stub to the registry, binding it to a name and ensuring a communication path
 * for the client. The overridden methods make calls to the local server model which makes calls to the database.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class RmiServer implements RemoteModel {

  private Model localModel;
  private PropertyChangeAction<Object, Object> property;

  /**
   * A constructor starting the registry, creating and uploading the server stub to it and binding it to a name.
   * @param localModel
   *         an instance of the local server model
   * @throws RemoteException if any error occurs during the execution of the constructor
   * @throws MalformedURLException if any malformed URL has occurred when doing the name binding
   */
  public RmiServer(Model localModel)
          throws RemoteException, MalformedURLException {
    startRegistry();
    this.localModel = localModel;
    UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("puppr", this);
    property = new PropertyChangeProxy<>(this, true);
  }

  /**
   * A private method used to start the registry
   * @throws RemoteException if any error occurs during the execution of the method
   */
  private void startRegistry() throws RemoteException {
    try {
      Registry reg = LocateRegistry.createRegistry(1099);
      System.out.println("Registry started...");

    } catch (java.rmi.server.ExportException e) {
      System.out.println("Registry already started? " + e.getMessage());
    }
  }


  /**
   * A method used to add a new user to the database
   * @param user
   *        the new User object that is to be added
   */
  @Override
  public void addUser(User user) {
    localModel.addUser(user);
  }

  /**
   * A method used to remove a user from the database
   * @param user
   *        the User that is to be removed
   */
  @Override
  public void removeUser(User user) {
    localModel.removeUser(user);
  }

  /**
   * A method used to update the user information in the database
   * @param user
   *        the modified User object
   */
  @Override
  public void editUser(User user) {
    localModel.editUser(user);
  }

  /**
   * Getter for all the users in the database
   * @return an ArrayList of User data type containing all the user information
   * from the database
   */
  @Override
  public ArrayList<User> getUserList() {
    return localModel.getUserList();
  }

  /**
   * Getter for a specific User object
   * @param handle
   *        the handle of the User object that is retrieved from the database
   * @return a User object with the handle value equal to the specified parameter value
   */
  @Override
  public User getUserByHandle(String handle) {
    return localModel.getUserByHandle(handle);
  }

  /**
   * A method for adding a like to a comment
   * @param commentId
   *        the id of the comment the like was added on
   */
  @Override
  public void addLikeToComment(int commentId)  {
    localModel.addLikeToComment(commentId);
  }

  /**
   * A method for increasing the like count of a post by 1
   * @param postId
   *        the id of the post on which the like was added
   */
  @Override
  public void addLikeToPost(int postId)  {
    localModel.addLikeToPost(postId);
  }

  /**
   * A method for adding a like to a Dog object
   * @param dogId
   *         the id of the Dog object the like was added on
   */
  @Override
  public void addLikeToDog(int dogId)  {
    localModel.addLikeToDog(dogId);
  }

  /**
   * A method for adding a new like for a post
   * @param postId
   *         the id on the post the like was added on
   * @param handle
   *        the handle of the user who liked the post
   */
  @Override
  public void addLike(int postId, String handle) {
    localModel.addLike(postId, handle);
  }

  /**
   * Getter for all the likes added on posts
   * @return an ArrayList of String data type, containing all the like info
   * for likes added on posts
   */
  @Override
  public ArrayList<String> getLikes() { return localModel.getLikes(); }

  /**
   * A method used to update the bio for a user
   * @param user
   *        the user whose bio was set/modified
   */
  @Override
  public void setBio(User user) { localModel.setBio(user); }

  /**
   * A method fro adding a new Comment to the database
   * @param comment
   *        the new comment that was added
   */
  @Override
  public void addComment(Comment comment) { localModel.addComment(comment); }

  /**
   * A method for update a comment information in the database
   * @param comment
   *        the edited comment
   */
  @Override
  public void editComment(Comment comment) { localModel.editComment(comment); }

  /**
   * A method for removing a comment from the database
   * @param comment
   *        the comment to be removed
   */
  @Override
  public void removeComment(Comment comment) { localModel.removeComment(comment); }

  /**
   * Getter for all the comment information from the database
   * @return an ArrayList of Comment data type containing all the comment information from
   * the database
   */
  @Override
  public ArrayList<Comment> getCommentList() { return localModel.getCommentList(); }

  /**
   * Getter for all the comments made on a specific post
   * @param postId
   *        the id of the post for which the comment list is retrieved
   * @return an ArrayList of Comment data type containing all the comment information
   * of comments made on the specified post
   */
  @Override
  public ArrayList<Comment> getCommentForPost(int postId) {
    return localModel.getCommentForPost(postId);
  }

  /**
   * A method for adding a new Dog object to the database
   * @param dog
   *        the new Dog object to be added
   */
  @Override
  public void addDog(Dog dog) {
    localModel.addDog(dog);
  }

  /**
   * A method for updating a Dog object's information
   * @param dog
   *        the Dog object that was edited
   */
  @Override
  public void editDog(Dog dog) {
    localModel.editDog(dog);
  }

  /**
   * A method for removing a Dog object from the database
   * @param dog
   *        the Dog object that is to be removed
   */
  @Override
  public void removeDog(Dog dog) {
    localModel.removeDog(dog);
  }

  /**
   * Getter for all the dogs related to a user
   * @param handle
   *        the handle of the owner of the dogs
   * @return an ArrayList of Dog data type containing all the Dog
   * information for dogs owned by the specified user
   */
  @Override
  public ArrayList<Dog> getDogsForUser(String handle) {
    return localModel.getDogsForUser(handle);
  }

  /**
   * Getter for a list of all the dogs
   * @return an ArrayList of Dog data type containing all the Dog information
   * form the database
   */
  @Override
  public ArrayList<Dog> getDogList() {
    return localModel.getDogList();
  }

  /**
   * A method for adding a new Post object to the database
   * @param post
   *        the new Post object that is to be added
   */
  @Override
  public void addPost(Post post) {
    localModel.addPost(post);
  }

  /**
   * A method for updating a post's information
   * @param post
   *        the edited version of the Post object
   */
  @Override
  public void editPost(Post post) {
    localModel.editPost(post);
  }

  /**
   * A method for removing a post form the database
   * @param post
   *        the Post object that is to be removed
   */
  @Override
  public void removePost(Post post) {
    localModel.removePost(post);
  }

  /**
   * Getter for a list of all the posts
   * @return an ArrayList of Post data type containing all the Post information
   * form the database
   */
  @Override
  public ArrayList<Post> getPostList() {
    return localModel.getPostList();
  }

  /**
   * Getter for a list of all the posts made by a specific user
   * @param handle
   *        the handle of the user who made the posts
   * @return an ArrayList of Post data type containing all the posts posted by
   * the specified user
   */
  @Override
  public ArrayList<Post> getPostsForUser(String handle) {
    return localModel.getPostsForUser(handle);
  }

  /**
   * Getter for a specific post
   * @param postId
   *        the id of the Post object that is retrieved
   * @return a Post object with the same id value as the specified parameter value
   */
  @Override
  public Post getPostById(int postId) {
    return localModel.getPostById(postId);
  }

  /**
   * A method for adding a like for a Dog object
   * @param dogId
   *        the id of the Dog object that was liked
   * @param handle
   *        the handle of the user who liked the dog
   */
  @Override
  public void addDogLike(int dogId, String handle) {
    localModel.addDogLike(dogId, handle);
  }

  /**
   * Getter for all the likes related to Dog objects
   * @return an ArrayList of String data type containing all the
   * dogId - handle pairs for dog likes
   */
  @Override
  public ArrayList<String> getDogLikes() {
    return localModel.getDogLikes();
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
  public void addCommentLike(int commentId, String handle) throws RemoteException {
      localModel.addCommentLike(commentId,handle);
  }

  /**
   * Getter for a list of all the comment likes
   * @return an ArrayList of String data type containing all the pairs in the
   * for of commentId - handle.
   * Example: "13 - sunny".
   */
  @Override
  public ArrayList<String> getCommentLikes() throws RemoteException {
    return localModel.getCommentLikes();
  }

  /**
   * A method for adding a new listener to this class
   * @param listener the listener that is being added
   * @param propertyNames the list of names of the properties
   *                      that the listener is being added for
   * @return a boolean value representing the successful addition of the listener
   */
  @Override
  public boolean addListener(GeneralListener<Object, Object> listener,
                             String... propertyNames) {
    return property.addListener(listener, propertyNames);
  }

  /**
   * A method for removing a listener for this class
   * @param listener the listener that is being removed
   * @param propertyNames the list of property names that the listener will not be
   *                      listening for anymore
   * @return a boolean value representing the successful removal of the listener
   */
  @Override
  public boolean removeListener(
          GeneralListener<Object, Object> listener, String... propertyNames) {
    return property.removeListener(listener, propertyNames);
  }


}
