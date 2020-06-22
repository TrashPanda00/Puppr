package database;

import model.Comment;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * CommentData class represents the connection between the server and the Comments table in the database.
 * The class contains a collection of methods for adding, getting, updating and deleting data from the table.
 * The DatabaseConnection class as well as <code>java.sql</code> classes are needed in order for the communication
 * to be established and work accordingly.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class CommentData
{
  private  DatabaseConnection db;
  private PreparedStatement pst;
  private Connection connection;
  private static final Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

  /**
   * A constructor for getting an instance of the DatabaseConnection and establishing a connection
   * @throws SQLException if any SQL error occurs while getting the DatabaseConnection
   */
  public CommentData() throws SQLException
  {
    db= DatabaseConnection.getInstance();
    connection=db.getConnection();
  }

  /**
   * A method for adding a new comment to the database.
   * @param comment
   *        the Comment object that is to be inserted into the Comments table
   * @throws SQLException if any SQL error occurs while executing the method
   */
  public void addComment(Comment comment) throws SQLException
  {
    String sql = "INSERT INTO COMMENTS(BODY,HANDLE,LIKES,TIME_POSTED,POST_ID) VALUES (?,?,?,?,?);";
    pst=connection.prepareStatement(sql);
    pst.setString(1,comment.getBody());
    pst.setString(2,comment.getHandle());
    pst.setInt(3,comment.getLikes());
    Timestamp ts = new Timestamp(comment.getTimePosted().toInstant(ZoneOffset.UTC).toEpochMilli());
    pst.setTimestamp(4, ts, utc);
    pst.setInt(5,comment.getPostId());
    db.operation(pst);
    System.out.println("ADDED: "+comment.getTimePosted()+"@"+comment.getHandle());

  }

  /**
   * A method for updating a comment's data in the table
   * @param comment
   *        the modified Comment object with the same <code>commentId</code>
   * @throws SQLException if any SQL error occurs while executing the method
   */
  public void editComment(Comment comment) throws SQLException
  {
    String sql = "UPDATE COMMENTS SET BODY=?,TIME_POSTED=? WHERE COMMENT_ID=?";
    pst=connection.prepareStatement(sql);

    pst.setString(1,comment.getBody());
    Timestamp ts = new Timestamp(comment.getTimePosted().toInstant(ZoneOffset.UTC).toEpochMilli());
    pst.setTimestamp(2, ts, utc);
    pst.setInt(3,comment.getCommentId());

    db.operation(pst);
    System.out.println("EDITED: "+comment.getTimePosted()+"@"+comment.getHandle());
  }

  /**
   * A method for deleting a row in the Comments table
   * @param comment
   *        the Comment object that is to be deleted from the database
   * @throws SQLException if any SQL error occurs while executing the method
   */
  public void removeComment(Comment comment) throws SQLException
  {
    String sql = "DELETE FROM COMMENTS WHERE COMMENT_ID=?";
    pst = connection.prepareStatement(sql);
    pst.setInt(1,comment.getCommentId());
    db.operation(pst);
    System.out.println("DELETED: "+comment.getTimePosted()+"@"+comment.getHandle());
  }

  /**
   * Getter for all the Comment objects in the Comments table
   * @return an ArrayList of Comment data type, which contains all the comments' information
   * from the Comments table in the database
   */
  public ArrayList<Comment> getCommentList(){
    ArrayList<Comment> comments=new ArrayList<>();
    try
    {
      Statement statement = connection.createStatement();
      connection.commit();
      ResultSet rs = statement.executeQuery("SELECT * FROM COMMENTS ORDER BY TIME_POSTED DESC;");

      Comment comment;
      while (rs.next())
      {
        Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);

        LocalDateTime localDt = null;
        if (ts != null)
          localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

        comment=new Comment(rs.getInt("COMMENT_ID"),rs.getString("BODY"),
                            rs.getString("HANDLE"),rs.getInt("LIKES"),localDt,
                            rs.getInt("POST_ID"));

        comments.add(comment);
      }
    }
    catch(Exception e){
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    return comments;
  }

  /**
   * Getter for all the Comment objects in the Comments table related to a specific post
   * @param postId
   *        the id of the post with which the comments are related to
   * @return an ArrayList of Comment data type containing all the comments having the same value
   * for <code>postId</code> as the specified parameter value
   */
  public ArrayList<Comment> getCommentForPost(int postId){

    ArrayList<Comment> comments=new ArrayList<>();
    try
    {
      Statement statement = connection.createStatement();
      connection.commit();
      ResultSet rs = statement.executeQuery("SELECT * FROM COMMENTS WHERE POST_ID='"+postId+"'  ORDER BY TIME_POSTED DESC;");

      Comment comment;
      while (rs.next())
      {
        Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);

        LocalDateTime localDt = null;
        if (ts != null)
          localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

        comment=new Comment(rs.getInt("COMMENT_ID"),rs.getString("BODY"),
                            rs.getString("HANDLE"),rs.getInt("LIKES"),localDt,
                            rs.getInt("POST_ID"));

        comments.add(comment);
      }
    }
    catch(Exception e){
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    return comments;
  }

  /**
   * A method for incrementing the likes of a comment by 1
   * @param commentId
   *        the Comment object for which the likes will be incremented
   * @throws SQLException if any SQL error occurs while executing the method
   */
  public void addLikeToComment(int commentId) throws SQLException
  {
    Comment comment=getComment(commentId);
    int likes=comment.getLikes();
    String sql = "UPDATE COMMENTS SET LIKES=?  WHERE COMMENT_ID=?";
    pst = connection.prepareStatement(sql);
    pst.setInt(1,++likes);
    pst.setInt(2,commentId);
    db.operation(pst);
    System.out.println("LIKED");
  }

  /**
   * Getter for a Comment object based on its <code>commentId</code>
   * @param commentId
   *        the unique id of the comment that is to be selected from the table
   * @return a Comment object with the same <code>commentId</code> as the specified parameter
   * value
   */
  public Comment getComment(int commentId){
    ArrayList<Comment> comments=new ArrayList<>();
    Comment comment=null;
    try
    {
      Statement statement = connection.createStatement();
      connection.commit();
      ResultSet rs = statement
          .executeQuery("SELECT * FROM COMMENTS WHERE COMMENT_ID='" +commentId + "'");

      while (rs.next())
      {
        Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);

        LocalDateTime localDt = null;
        if (ts != null)
          localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

        comment=new Comment(rs.getInt("COMMENT_ID"),rs.getString("BODY"),
                            rs.getString("HANDLE"),rs.getInt("LIKES"),localDt,
                            rs.getInt("POST_ID"));

        comments.add(comment);
        comment=comments.get(0);
      }
    }
    catch (Exception e)
    {

      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return comment;
  }
}
