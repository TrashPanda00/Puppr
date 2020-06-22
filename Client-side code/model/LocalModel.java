package model;

import utility.observer.subject.LocalSubject;

import java.util.ArrayList;

/**
 * LocalModel defines a collection of methods accessing, modifying and getting objects from the model package.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public interface LocalModel
{
  /**
   * A setter for the user bio
   * @param user
   *            the user for who the bio was modified or set
   */
  void setBio(User user);

  /**
   * A method for adding a new comment to a post
   * @param comment
   *        the comment that was added to the post
   */
  void addComment(Comment comment);

  /**
   * A method for editing a comment which was already posted
   * @param comment
   *        the comment on which the modification was made on
   */
  void editComment(Comment comment);

  /**
   * A method for removing a comment
   * @param comment
   *        the comment which was deleted from the post
   */
  void removeComment(Comment comment);

  /**
   * A method for getting the list of comments for all post
   * @return an ArrayList with Comment as data type, representing all the comments for all the posts on the Puppr platform
   */
  ArrayList<Comment> getCommentList();

  /**
   * A method for getting the list of comment for a particular post
   * @param postId
   *        the post id of the post for which the comments will be retrieved
   * @return an ArrayList with Comment as the data type, which contains all the comments for the post specified by the postId
   */
  ArrayList<Comment> getCommentForPost(int postId);

  /**
   * A method for linking a new Dog to a user profile
   * @param dog
   *        the new Dog object that was added
   */
  void addDog(Dog dog);

  /**
   * A method for editing a Dog object
   * @param dog
   *        the dog object that was modified
   */
  void editDog(Dog dog);

  /**
   * A method for removing a Dog object linked to a user profile
   * @param dog
   *        the dog that is to be removed from a user profile
   */
  void removeDog(Dog dog);

  /**
   * A method for getting all the Dog objects linked to a user
   * @param handle
   *        the handle of the user for which the list of dogs is needed.
   *        Handle acts as the unique identifier for the User objects
   * @return an ArrayList of Dog data type containing all the Dog objects related to the User described the the given handle parameter.
   */
  ArrayList<Dog> getDogsForUser(String handle);

  /**
   * A method for getting the complete list of all the dogs in the Puppr database
   * @return an ArrayList of Dog data type encapsulating all the Dog objects in the database
   */
  ArrayList<Dog> getDogList();

  /**
   * A method for adding a new post
   * @param post
   *        the new post to be added
   */
  void addPost(Post post);

  /**
   * A method for editing a post
   * @param post
   *        the post object that was edited
   */
  void editPost(Post post);

  /**
   * A method for removing an already existing post
   * @param post
   *        the post that is to be removed
   */
  void removePost(Post post);

  /**
   * A method for getting a list of all the post ever added
   * @return an ArrayList of Post data type containing all the posts that were ever added by all the different users
   */
  ArrayList<Post> getPostList();

  /**
   * A method for getting a list of all the posts for a specific user
   * @param handle
   *        the handle of the user for which the list of the posts is needed
   * @return an ArrayList of Post data type containing all the posts ever added by the specified user
   */
  ArrayList<Post> getPostsForUser(String handle);

  /**
   * A method used to retrieve a singular post based on its id
   * @param postId
   *        the unique id specific to each post, used to differentiate between posts
   * @return the post which has the specified <code>postId</code>
   */
  Post getPostById(int postId);

  /**
   * A method for adding a new user, signaling a new user completed the sign up process
   * @param user
   *        the new User object to be added, containing all the information completed by the person on sign up
   */
  void addUser(User user);

  /**
   * A method used to remove an already existing user
   * @param user
   *        the user that will be removed
   */
  void removeUser(User user) ;

  /**
   * A method for editing an user
   * @param user
   *        the user that was modified
   */
  void editUser(User user);

  /**
   * A method used to get the complete list of all the user of the puppr app
   * @return an ArrayList of User data type which contains all the user in the Puppr database
   */
  ArrayList<User> getUserList();

  /**
   * A method for getting a user based on the user handle
   * @param handle
   *        the unique handle used to identify a user from another
   * @return an User object with the handle attribute equal to the handle parameter given
   */
  User getUserByHandle(String handle);

  /**
   * A method for adding a like to a comment
   * @param commentId
   *        the comment id of the Comment object on which the like was added
   */
  void addLikeToComment(int commentId);

  /**
   * A method for adding a like to a post
   * @param postId
   *        the unique post id of the Post object on which the like was added
   */
  void addLikeToPost(int postId);

  /**
   * A method used to add a like to a Dog object
   * @param dogId
   *        the unique dog id of the dog on which the like was added
   */
  void addLikeToDog(int dogId);


  /**
   * A method used to add a liked to a post and save the user info of the user who liked the post
   * @param postId
   *        the id of the post the like was added on
   * @param handle
   *        the handle of the user who liked the specified post
   */
  void addLike(int postId, String handle);

  /**
   * A method for getting all the postId and handle info for all the likes added
   * @return an ArrayList of String data type containing all the postId and handle pairs for every like
   *         on all the posts ever added
   */
  ArrayList<String> getLikes();

  /**
   * A method used to add a like to a Dog object and save the user info of the user who liked the dog
   * @param dogId
   *        the id of the Dog object that the like was added on
   * @param handle
   *        the handle of the user that liked the specified dog
   */
  void addDogLike(int dogId, String handle);

  /**
   * A method for getting a list of all the dogId and handle info of all the likes for dogs
   * @return the dogId and handle pairs for each like added on all the Dog objects that exist in the database
   */
  ArrayList<String> getDogLikes();

  /**
   * A method for adding a new like to a comment and saving the commentId - handle
   * information in the database.
   * @param commentId
   *        the id of the comment that was added
   * @param handle
   *        the handle of the user who liked the comment
   */
  void addCommentLike(int commentId, String handle);

  /**
   * Getter for a list of all the comment likes
   * @return an ArrayList of String data type containing all the pairs in the
   * form of commentId - handle.
   * Example: "13 - sunny".
   */
  ArrayList<String> getCommentLikes();

  /**
   * A method used to close the connection between the client and the server side of the app
   */
  void close();
}
