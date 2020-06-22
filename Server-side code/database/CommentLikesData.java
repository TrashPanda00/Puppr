package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * CommentLikesData class handles the comment like (likes made on comments). The class ensures the communication
 * of the server with the CommentLikes table which contains information about the commentId and the handle of the user who
 * liked the comment.
 * A link to the DatabaseConnection class and to <code>java.sql</code> classes is needed in order for the communication to be
 * established and functional.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class CommentLikesData {

    private  DatabaseConnection db;
    private PreparedStatement pst;
    private Connection connection;


    /**
     * A constructor for getting an instance of the DatabaseConnection and establishing a connection
     * @throws SQLException if any SQL error occurs while getting the DatabaseConnection
     */
    public CommentLikesData() throws SQLException {
        db = DatabaseConnection.getInstance();
        connection = db.getConnection();
    }

    /**
     * A method for adding a new row to the CommentLikes table
     * @param commentId
     *        the id of the comment that was liked
     * @param handle
     *        the handle of the user who liked the comment
     * @throws SQLException if any SQL error occurs while getting the DatabaseConnection
     */
    public void addLike(int commentId, String handle) throws SQLException {
        String sql = "INSERT INTO COMMENTLIKES(COMMENT_ID,HANDLE) VALUES (?,?);";
        pst = connection.prepareStatement(sql);
        pst.setInt(1, commentId);
        pst.setString(2, handle);
        db.operation(pst);
        System.out.println("Comment like info added");
    }

    /**
     * Getter for all the comment likes in the table
     * @return an ArrayList of String data type containing all the like information
     * in the CommentLikes table
     */
    public ArrayList<String> getLikes(){

        ArrayList<String> likes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            connection.commit();

            ResultSet rs = statement.executeQuery("SELECT * FROM COMMENTLIKES;");

            String like;
            while (rs.next()) {
                like = rs.getInt("COMMENT_ID") + "/" + rs.getString("HANDLE");

                likes.add(like);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return likes;
    }
}
