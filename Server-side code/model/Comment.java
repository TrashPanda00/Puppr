package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Comment class is a class that holds and manages the comment data needed for a singular
 * comment such as the comment body, the handle of the person who posted the comment,
 * number of likes and comments for said post, etc.
 * The class implements <code>Serializable</code> in order to be send and received from the Server.
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class Comment implements Serializable
{

    private int commentId;
    private String body;
    private String handle;
    private LocalDateTime timePosted;
    private int likes;
    private int postId;


    /**
     * A constructor used to retrieve the comment data from the server/database and set each variable
     * to its correct value
     *
     * @param commentId
     *        the unique id given to every comment which will act as the primary key for the
     *        comment in the database.
     * @param body
     *        the comment body as a String. This is not unique but cannot be <code>null</code>.
     * @param handle
     *        the handle of the user who posted the comment
     *        Example: @dogLover => handle: dogLover
     * @param likes
     *        the number of likes the comment has at this current time (or 0 if it is a new comment).
     * @param timePosted
     *        the time that the comment was posted at.
     * @param postId
     *        the unique identifier for each post helping link a comment to the post it was added to,
     *        also acting as the foreign key for the Comment table in the database.
     */
    public Comment(int commentId,String body, String handle,int likes,LocalDateTime timePosted,int postId){
        this.commentId=commentId;
        this.body=body;
        this.handle=handle;
        this.likes=likes;
        this.timePosted=timePosted;
        this.postId=postId;
    }

    /**
     * A constructor used for creating a new comment, setting the number of likes to 0
     * and the time posted to the current time while not setting the commentId (for now).
     */
    public Comment(String body, String handle,int postId){
        this.body=body;
        this.handle=handle;
        this.likes=0;
        timePosted=LocalDateTime.now();
        this.postId=postId;
    }


    /**
     * Another constructor used to create a new comment which also sets the commentId
     * to a value specified by the user.
     */
    public Comment(int commentId,String body, String handle,int likes,int postId){
        this.commentId=commentId;
        this.body=body;
        this.handle=handle;
        this.likes=likes;
        this.timePosted=LocalDateTime.now();
        this.postId=postId;
    }


    /**
     * Getter for the <code>timePosted</code> variable.
     * @return the time posted for the comment.
     */
    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    /**
     * Getter for the body of a comment.
     * @return a reference to the <code>body</code> of the current comment
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter for the handle of the comment.
     * @return the <code>handle</code> attribute of the current comment
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Getter for the id of the post the comment was left on.
     * @return the <code>postId</code> of the post the comment is linked to
     */
    public int getPostId()
    {
        return postId;
    }


    /**
     * Getter for the number of likes of the comment
     * @return the current number of likes for the comment
     */
    public int getLikes()
    {
        return likes;
    }

    /**
     * Getter for the <code>commentId</code>.
     * @return the comment id of the current comment
     */
    public int getCommentId()
    {
        return commentId;
    }

    /**
     * Setter for the <code>body</code> attribute.
     * @param body the new comment body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Setter for the number of likes of the comment.
     * @param likes the new number of likes
     */
    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    /**
     * A <code>String</code> representation of the comment with elements separated by
     * commas and enclosed in a set of curly braces
     * Example: "{commentId=1, body='Hello', timePosted=18:39:34, handle='andrew01', likes=10, postId=7}"
     *
     * @return the string version of the comment object
     */
    @Override public String toString()
    {
        return "{" + "commentId=" + commentId + ", body='" + body + '\''
                + ", timePosted=" + timePosted + '\''
                + ", handle='" + handle + '\''
                + ", likes=" + likes + ", postId=" + postId + '}';
    }
}
