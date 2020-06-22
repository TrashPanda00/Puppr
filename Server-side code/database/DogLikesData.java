package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * The DogLikesData class handle the communication between the server and the DogLikes table in the database.
 * It contains a set of method for adding, getting and removing likes from Dog object.
 * The communication is made possible using instances of the DatabaseConnection and java.sql.Connection classes.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class DogLikesData
{
  private  DatabaseConnection db;
  private PreparedStatement pst;
  private Connection connection;


  /**
   * A constructor setting the communication between the class and the table
   * @throws SQLException if any error occurs while executing the constructor
   */
  public DogLikesData() throws SQLException
  {
    db = DatabaseConnection.getInstance();
    connection = db.getConnection();
  }

  /**
   * A method for adding a dog like information to the table
   * @param dogId
   *        the id of the Dog object that was liked
   * @param handle
   *        the handle of the user who liked the dog
   * @throws SQLException if any error occurs while executing the method
   */
  public void addLike(int dogId, String handle) throws SQLException {
    String sql = "INSERT INTO DOGLIKES(HANDLE, DOG_ID) VALUES (?,?);";
    pst = connection.prepareStatement(sql);
    pst.setInt(2, dogId);
    pst.setString(1, handle);
    db.operation(pst);
    System.out.println("Like info added");
  }

  /**
   * Getter for all the dog likes information from the table
   * @return an ArrayList of String data type containing all the
   * dogId - handle pairs for the likes
   */
  public ArrayList<String> getLikes(){

    ArrayList<String> likes = new ArrayList<>();

    try {
      Statement statement = connection.createStatement();
      connection.commit();

      ResultSet rs = statement.executeQuery("SELECT * FROM DOGLIKES;");

      String like;
      while (rs.next()) {
        like = rs.getInt("DOG_ID") + "/" + rs.getString("HANDLE");

        likes.add(like);
      }
    } catch (Exception e) {

      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return likes;
  }

  /**
   * A method for removing all the likes related to a dog object from the table
   * @param dogId
   *        the id of the dog whose likes are being removed
   * @throws SQLException if any error occurs while executing the method
   */
  public void cleanDogLikes(int dogId) throws SQLException
  {
    String sql = "DELETE FROM DOGLIKES WHERE DOG_ID =?";
    pst = connection.prepareStatement(sql);
    pst.setInt(1,dogId);
    db.operation(pst);
    System.out.println("DELETED: "+dogId);
  }
}
