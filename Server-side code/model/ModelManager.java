package model;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The ModelManager class is an implementation of the Model interface with the purpose of connecting the local
 * model methods to the database methods, therefore allowing the RemoteModel to make changes to the database indirectly
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class ModelManager implements Model
{

  private UserData userData;
  private DogData dogData;
  private PostData postData;
  private CommentData commentData;
  private LikesData likesData;
  private DogLikesData dogLikesData;
  private CommentLikesData commentLikesData;

  /**
   * A constructor initialising all the instance for the database classes
   * @throws SQLException if any error of SQL type occurs during initialisation
   */
  public ModelManager() throws SQLException
  {
    this.userData=new UserData();
    this.dogData=new DogData();
    this.postData=new PostData();
    this.commentData=new CommentData();
    this.likesData=new LikesData();
    this.dogLikesData=new DogLikesData();
    this.commentLikesData= new CommentLikesData();
  }

  /**
   * A method used to add a User to the database
   * @param user
   *        the user object that is being added
   */
  @Override public void addUser(User user)
  {
    try
    {
      userData.addUser(user);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

  }

  /**
   * A method used to update the user information in the database
   * @param user
   *        the modified User object
   */
  @Override public void editUser(User user)
  {
    try
    {
      userData.editUser(user);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method used to remove a user from the database
   * @param user
   *        the User that is to be removed
   */
  @Override public void removeUser(User user)
  {
    try
    {
      userData.removeUser(user);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the users in the database
   * @return an ArrayList of User data type containing all the user information
   * from the database
   */
  @Override public ArrayList<User> getUserList()
  {
    return userData.getUserList();
  }

  /**
   * Getter for a specific User object from the database
   * @param handle
   *        the handle of the User object that is retrieved from the database
   * @return a User object with the handle value equal to the specified parameter value
   */
  @Override public User getUserByHandle(String handle)
  {

    return userData.getUserByHandle(handle);
  }

  /**
   * A method for adding a like to a comment in the databse
   * @param commentId
   *        the id of the comment the like was added on
   */
  @Override public void addLikeToComment(int commentId)
  {
    try
    {
      commentData.addLikeToComment(commentId);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for increasing the like count of a post by 1
   * @param postId
   *        the id of the post on which the like was added
   */
  @Override public void addLikeToPost(int postId)
  {
    try
    {
      postData.addLikeToPost(postId);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for adding a like to a Dog object in the database
   * @param dogId
   *         the id of the Dog object the like was added on
   */
  @Override public void addLikeToDog(int dogId)
  {
    try
    {
      dogData.addLikeToDog(dogId);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }


  /**
   * A method for adding a new like for a post in the database
   * @param postId
   *         the id on the post the like was added on
   * @param handle
   *        the handle of the user who liked the post
   */
  @Override
  public void addLike(int postId, String handle) {
    try {
      likesData.addLike(postId,handle);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the likes added on posts
   * @return an ArrayList of String data type, containing all the like information
   * from the database for likes added on posts
   */
  @Override
  public ArrayList<String> getLikes() {
    return likesData.getLikes();
  }

  /**
   * A method used to update the bio in the database for a user
   * @param user
   *        the user whose bio was set/modified
   */
  @Override
  public void setBio(User user) {
    try {
      userData.editBio(user);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * A method fro adding a new Comment to the database
   * @param comment
   *        the new comment that was added
   */
  @Override public void addComment(Comment comment)
  {
    try
    {
      commentData.addComment(comment);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for update a comment information in the database
   * @param comment
   *        the edited comment
   */
  @Override public void editComment(Comment comment)
  {
    try
    {
      commentData.editComment(comment);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for removing a comment from the database
   * @param comment
   *        the comment to be removed
   */
  @Override public void removeComment(Comment comment)
  {
    try
    {
      commentData.removeComment(comment);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the comment information from the database
   * @return an ArrayList of Comment data type containing all the comment information from
   * the database
   */
  @Override public ArrayList<Comment> getCommentList()
  {
    return commentData.getCommentList();
  }

  /**
   * Getter for all the comments made on a specific post
   * @param postId
   *        the id of the post for which the comment list is retrieved
   * @return an ArrayList of Comment data type containing all the comment information
   * of comments made on the specified post
   */
  @Override public ArrayList<Comment> getCommentForPost(int postId)
  {
    return commentData.getCommentForPost(postId);
  }

  /**
   * A method for adding a new Dog object to the database
   * @param dog
   *        the new Dog object to be added
   */
  @Override public void addDog(Dog dog)
  {
    try
    {
      dogData.addDog(dog);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for updating a Dog object's information
   * @param dog
   *        the Dog object that was edited
   */
  @Override public void editDog(Dog dog)
  {
    try
    {
      dogData.editDog(dog);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for removing a Dog object from the database
   * @param dog
   *        the Dog object that is to be removed
   */
  @Override public void removeDog(Dog dog)
  {
    try
    {
      dogData.removeDog(dog);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the dogs related to a user
   * @param handle
   *        the handle of the owner of the dogs
   * @return an ArrayList of Dog data type containing all the Dog
   * information for dogs owned by the specified user
   */
  @Override public ArrayList<Dog> getDogsForUser(String handle)
  {
    return dogData.getDogsForUser(handle);
  }

  /**
   * Getter for a list of all the dogs
   * @return an ArrayList of Dog data type containing all the Dog information
   * form the database
   */
  @Override public ArrayList<Dog> getDogList()
  {
    return dogData.getDogList();
  }

  /**
   * A method for adding a new Post object to the database
   * @param post
   *        the new Post object that is to be added
   */
  @Override public void addPost(Post post)
  {
    try
    {
      postData.addPost(post);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for updating a post's information
   * @param post
   *        the edited version of the Post object
   */
  @Override public void editPost(Post post)
  {
    try
    {
      postData.editPost(post);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * A method for removing a post form the database
   * @param post
   *        the Post object that is to be removed
   */
  @Override public void removePost(Post post)
  {
    try
    {
      postData.removePost(post);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for a list of all the posts
   * @return an ArrayList of Post data type containing all the Post information
   * form the database
   */
  @Override public ArrayList<Post> getPostList()
  {
    return postData.getPostList();
  }

  /**
   * Getter for a list of all the posts made by a specific user
   * @param handle
   *        the handle of the user who made the posts
   * @return an ArrayList of Post data type containing all the posts posted by
   * the specified user
   */
  @Override public ArrayList<Post> getPostsForUser(String handle)
  {
    return postData.getPostsForUser(handle);
  }

  /**
   * Getter for a specific post
   * @param postId
   *        the id of the Post object that is retrieved
   * @return a Post object with the same id value as the specified parameter value
   */
  @Override public Post getPostById(int postId)
  {
    return postData.getPostById(postId);
  }

  /**
   * A method for adding a like for a Dog object
   * @param dogId
   *        the id of the Dog object that was liked
   * @param handle
   *        the handle of the user who liked the dog
   */
  @Override public void addDogLike(int dogId, String handle)
  {
    try
    {
      dogLikesData.addLike(dogId,handle);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for all the likes related to Dog objects
   * @return an ArrayList of String data type containing all the
   * dogId - handle pairs for dog likes
   */
  @Override public ArrayList<String> getDogLikes()
  {
    return dogLikesData.getLikes();
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
    try {
      commentLikesData.addLike(commentId,handle);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Getter for a list of all the comment likes
   * @return an ArrayList of String data type containing all the pairs in the
   * for of commentId - handle from the database.
   * Example: "13 - sunny".
   */
  @Override
  public ArrayList<String> getCommentLikes() {
    return commentLikesData.getLikes();
  }

}
