package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * The LikesData class handles the communication between the server and the Likes table from the database.
 * It contains methods for adding and getting likes information from the table.
 * The communication is made possible using instances of the DatabaseConnection and java.sql.Connection classes.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */

public class LikesData {
    private  DatabaseConnection db;
    private PreparedStatement pst;
    private Connection connection;


    /**
     * A constructor setting up the communication
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public LikesData() throws SQLException {
        db = DatabaseConnection.getInstance();
        connection = db.getConnection();
    }

    /**
     * A method for adding a new like's information to the table
     * @param postId
     *         the id of the post that was likes
     * @param handle
     *          the handle of the user who likes the post
     * @throws SQLException if any SQL related error occurs while executing the method
     */
    public void addLike(int postId, String handle) throws SQLException {
        String sql = "INSERT INTO LIKES(HANDLE, POST_ID) VALUES (?,?);";
        pst = connection.prepareStatement(sql);
        pst.setInt(2, postId);
        pst.setString(1, handle);
        db.operation(pst);
        System.out.println("Like info added");
    }

    /**
     * Getter for all the likes in the table
     * @return an ArrayList of String data type containing all the
     * postId - handle pairs from the table.
     */
    public ArrayList<String> getLikes(){

        ArrayList<String> likes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            connection.commit();

            ResultSet rs = statement.executeQuery("SELECT * FROM LIKES;");

            String like;
            while (rs.next()) {
                like = rs.getInt("POST_ID") + "/" + rs.getString("HANDLE");

                likes.add(like);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return likes;
    }
}
