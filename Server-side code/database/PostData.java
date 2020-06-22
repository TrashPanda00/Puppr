package database;


import model.Post;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * The PostData handles the communication between the server and the Posts table in the database. It
 * contains a set of methods for adding, getting, updating and removing post information from the table.
 * This communication is achieved with the help of the two DatabaseConnection and
 * <code>java.sql.Connection</code> class instances.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class PostData {

    private  DatabaseConnection db;
    private PreparedStatement pst;
    private Connection connection;
    private CommentData commentData;
    private static final Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    /**
     * A constructor setting up the communication between the class and the database table
     * @throws SQLException if any SQL related error occurs while setting up the connection
     */
    public PostData() throws SQLException {
        db = DatabaseConnection.getInstance();
        connection = db.getConnection();
        commentData = new CommentData();
    }

    /**
     * A method for adding a new post row in the table
     * @param post
     *        the new Post object that was added
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public void addPost(Post post) throws SQLException {
        String sql = "INSERT INTO POSTS(IMAGE_URL,HANDLE,LIKES,TIME_POSTED,TEXT) VALUES (?,?,?,?,?);";
        pst = connection.prepareStatement(sql);
        pst.setBytes(1, post.getImageURL());
        pst.setString(2, post.getHandle());
        pst.setInt(3, post.getLikes());
        Timestamp ts = new Timestamp(post.getTimePosted().toInstant(ZoneOffset.UTC).toEpochMilli());
        pst.setTimestamp(4, ts, utc);
        pst.setString(5, post.getText());
        db.operation(pst);
        System.out.println("ADDED: " + post.getTimePosted() + " @" + post.getHandle());
    }

    /**
     * A method for updating the information for an already existing post
     * @param post
     *        the modified Post object
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public void editPost(Post post) throws SQLException {

        String sql = "UPDATE POSTS SET IMAGE_URL=?,HANDLE=?,LIKES=?,TIME_POSTED=?,TEXT=?  WHERE POST_ID=?";
        pst = connection.prepareStatement(sql);
        pst.setBytes(1, post.getImageURL());
        pst.setString(2, post.getHandle());
        pst.setInt(3, post.getLikes());
        Timestamp ts = new Timestamp(post.getTimePosted().toInstant(ZoneOffset.UTC).toEpochMilli());
        pst.setTimestamp(4, ts, utc);
        pst.setString(5, post.getText());
        pst.setInt(6, post.getPostId());

        db.operation(pst);
        System.out.println("EDITED: " + post.getTimePosted() + " @" + post.getHandle());
    }

    /**
     * A method for removing a post row from the table
     * @param post
     *        the post that is to be removed
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public void removePost(Post post) throws SQLException {
        String sql = "DELETE FROM POSTS WHERE POST_ID=?";
        pst = connection.prepareStatement(sql);
        pst.setInt(1, post.getPostId());
        // db.delete(pst);
        db.operation(pst);
        System.out.println("DELETED: " + post.getTimePosted() + " @" + post.getHandle());
    }

    /**
     * Getter for all the post information from the table. If any error occurs while
     * executing the method, the error message is printed out in the console.
     * @return an ArrayList of Post data type containing all the post objects from the
     * database
     */
    public ArrayList<Post> getPostList() {

        ArrayList<Post> posts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            connection.commit();

            ResultSet rs = statement.executeQuery("SELECT * FROM POSTS ORDER BY TIME_POSTED ;");

            Post post;
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);

                LocalDateTime localDt = null;
                if (ts != null)
                    localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);

                post = new Post(rs.getInt("POST_ID"), rs.getBytes("IMAGE_URL"),
                                rs.getString("HANDLE"), rs.getInt("LIKES"), localDt,
                                rs.getString("TEXT"), commentData.getCommentForPost(rs.getInt("POST_ID")));


                posts.add(post);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return posts;
    }

    /**
     * Getter for all the posts made by a specific user. If any error occurs while
     * executing the method, the error message is printed out in the console.
     * @param handle
     *        the handle of the user who made the posts
     * @return an ArrayList of Post data type containing all the posts ever made by the specified user
     */
    public ArrayList<Post> getPostsForUser(String handle) {

        ArrayList<Post> posts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            connection.commit();

            ResultSet rs = statement.executeQuery("SELECT * FROM POSTS WHERE HANDLE='" + handle + "'  ORDER BY TIME_POSTED ;");

            Post post;
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);
                LocalDateTime localDt = null;
                if (ts != null)
                    localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
                post = new Post(rs.getInt("POST_ID"), rs.getBytes("IMAGE_URL"),
                                rs.getString("HANDLE"), rs.getInt("LIKES"), localDt,
                                rs.getString("TEXT"), commentData.getCommentForPost(rs.getInt("POST_ID")));

                posts.add(post);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return posts;

    }

    /**
     * Getter for a Post object based on its id. If any error occurs while
     * executing the method, the error message is printed out in the console.
     * @param postId
     *        the id of the Post object whose information will be retrieved from the table
     * @return a Post object
     */
    public Post getPostById(int postId) {

        ArrayList<Post> posts = new ArrayList<>();
        Post post=null;
        try {
            Statement statement = connection.createStatement();
            connection.commit();

            ResultSet rs = statement.executeQuery("SELECT * FROM POSTS WHERE POST_ID=" + postId);


            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("TIME_POSTED", utc);
                LocalDateTime localDt = null;
                if (ts != null)
                    localDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime()), ZoneOffset.UTC);
                post = new Post(rs.getInt("POST_ID"), rs.getBytes("IMAGE_URL"),
                                rs.getString("HANDLE"), rs.getInt("LIKES"), localDt,
                                rs.getString("TEXT"), commentData.getCommentForPost(rs.getInt("POST_ID")));

                posts.add(post);
                post=posts.get(0);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return post;

    }

    /**
     * A method for incrementing the likes count of a Post object in the table by 1
     * @param postId
     *         the id of the post whose likes count is being incremented
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public void addLikeToPost(int postId) throws SQLException {

        Post post = getPostById(postId);

        int likes = post.getLikes();

        String sql = "UPDATE POSTS SET LIKES=?  WHERE POST_ID=?";
        pst = connection.prepareStatement(sql);
        pst.setInt(1, ++likes);
        pst.setInt(2, post.getPostId());
        db.operation(pst);
        System.out.println("LIKED");
    }


}
