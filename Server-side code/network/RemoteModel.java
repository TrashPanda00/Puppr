package network;

import model.*;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * RemoteModel is the interface that allows the client and
 * the server to communicate using RMI connections. It contains a set
 * of remote methods that are implemented on both the client and server side and
 * make for the "protocol" of the client-server communication.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public interface RemoteModel extends RemoteSubject<Object, Object>
{
    /**
     * Setter for the bio attribute of a User object.
     * @param user
     *        the user whose bio was set/modified
     * @throws RemoteException if any error appears during the execution
     * of the remote method.
     */
    void setBio(User user) throws RemoteException;

    /**
     * A method for adding a comment to a post
     * @param comment
     *        the new comment that was added
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addComment(Comment comment) throws RemoteException;

    /**
     * A method for editing an already existing comment on a post
     * @param comment
     *        the edited comment
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void editComment(Comment comment) throws RemoteException;

    /**
     * A method for removing a comment on a post
     * @param comment
     *        the comment to be removed
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void removeComment(Comment comment) throws RemoteException;

    /**
     * Getter for the full list of comments ever posted.
     * @return an ArrayList of Comment data type containing all the comments
     * ever posted on the Puppr app.
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Comment> getCommentList() throws RemoteException;

    /**
     * Getter for the list of comments for a particular post.
     * @param postId
     *        the id of the post for which the comment list is retrieved
     * @return an ArrayList of Comment data type containing all the comments
     * for the specified comments
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Comment> getCommentForPost(int postId) throws RemoteException;

    /**
     * A method for adding a new dog to a user's profile
     * @param dog
     *        the new Dog object to be added
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addDog(Dog dog) throws RemoteException;

    /**
     * A method for editing an already existing dog
     * @param dog
     *        the Dog object that was edited
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void editDog(Dog dog) throws RemoteException;

    /**
     * A method for removing a dog from a user's profile
     * @param dog
     *        the Dog object that is to be removed
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void removeDog(Dog dog) throws RemoteException;

    /**
     * Getter for the list of dogs of a specific user
     * @param handle
     *        the handle of the owner of the dogs
     * @return an ArrayList of Dog data type containing all the dogs
     * linked to the specified user's profile
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Dog> getDogsForUser(String handle) throws RemoteException;

    /**
     * Getter for the full list of dogs
     * @return an ArrayList of Dog data type containing all the Dog objects
     * in the database
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Dog> getDogList() throws RemoteException;

    /**
     * A method for adding a new post
     * @param post
     *        the new Post object that is to be added
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addPost(Post post) throws RemoteException;

    /**
     * A method for editing an already existing post
     * @param post
     *        the edited version of the Post object
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void editPost(Post post) throws RemoteException;

    /**
     * A method for removing an already existing post
     * @param post
     *        the Post object that is to be removed
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void removePost(Post post) throws RemoteException;

    /**
     * Getter for the full list of posts
     * @return an ArrayList of Post data type containing all the
     * posts in the database
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Post> getPostList() throws RemoteException;

    /**
     * Getter for the list of posts made by a specific used
     * @param handle
     *        the handle of the user who made the posts
     * @return an ArrayList of Post data type containing
     * all the posts ever made by the user with the specified handle
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<Post> getPostsForUser(String handle) throws RemoteException;

    /**
     * Getter for a Post object based on its unique id
     * @param postId
     *        the id of the Post object that is retrieved
     * @return a Post object with the <code>postId</code> value equal to
     * the specified parameter value
     * @throws RemoteException if any error appears during the execution of
     * the remote method
     */
    Post getPostById(int postId) throws RemoteException;

    /**
     * A method for adding a new user
     * @param user
     *        the new User object that is to be added
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addUser(User user) throws RemoteException;

    /**
     * A method for removing an already existing user
     * @param user
     *        the User that is to be removed
     * @throws RemoteException if any error appears during the execution of
     * the remote method
     */
    void removeUser(User user) throws RemoteException;

    /**
     * A method for editing the information related to an already existing
     * user
     * @param user
     *        the modified User object
     * @throws RemoteException if any error appears during the execution of
     * the remote method
     */
    void editUser(User user) throws RemoteException;

    /**
     * Getter for the full list of Puppr users
     * @return an ArrayList of User data type containing all the user information
     * for all the users of the app
     * @throws RemoteException if any error appears during the execution of
     * the remote method
     */
    ArrayList<User> getUserList() throws RemoteException;

    /**
     * Getter for a user based on its unique handle
     * @param handle
     *        the handle of the User object that is retrieved
     * @return an User object with the same handle value as the specified
     * parameter value
     * @throws RemoteException if any error appears during the execution of
     * the remote method
     */
    User getUserByHandle(String handle) throws RemoteException;

    /**
     * A method for adding a like to a comment
     * @param commentId
     *        the id of the comment the like was added on
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addLikeToComment(int commentId) throws RemoteException;

    /**
     * A method for adding a new like to a post. This method increases the
     * <code>like</code> attribute value by 1.
     * @param postId
     *        the id of the post on which the like was added
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addLikeToPost(int postId) throws RemoteException;

    /**
     * A method for adding a new like to a Dog object. This method increases the
     * <code>like</code> attribute value by 1.
     * @param dogId
     *         the id of the Dog object the like was added on
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addLikeToDog(int dogId) throws RemoteException;

    /**
     * A method for adding a new like to a post. This method saves
     * the like information as a pair of the postId and user handle.
     * Example: "12 - Jennifer03"
     * @param postId
     *         the id on the post the like was added on
     * @param handle
     *        the handle of the user who liked the post
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addLike(int postId, String handle) throws RemoteException;

    /**
     * Getter for all the likes made on all the posts
     * @return an ArrayList of String data type containing all the
     * postId - handle pairs for the likes.
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<String> getLikes() throws RemoteException;

    /**
     * A method for adding a new like to a Dog object. This method saves the
     * like information as a pair of dogId - user handle.
     * Example: "32 - andrew_2000"
     * @param dogId
     *        the id of the Dog object that was liked
     * @param handle
     *        the handle of the user who liked the dog
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addDogLike(int dogId, String handle) throws RemoteException;

    /**
     * Getter for all thr likes made on all the Dog objects
     * @return an ArrayList of String data type containing all the
     * dogId - handle pairs of likes
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<String> getDogLikes() throws RemoteException;

    /**
     * A method for adding a new like to a comment and saving the commentId - handle
     * information in the database.
     * @param commentId
     *        the id of the comment that was added
     * @param handle
     *        the handle of the user who made the comment
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    void addCommentLike(int commentId, String handle) throws RemoteException;

    /**
     * Getter for a list of all the comment likes
     * @return an ArrayList of String data type containing all the pairs in the
     * for of commentId - handle.
     * Example: "13 - sunny".
     * @throws RemoteException if any error appears during the execution
     * of the remote method
     */
    ArrayList<String> getCommentLikes() throws RemoteException;
}
