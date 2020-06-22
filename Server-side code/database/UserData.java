package database;

import model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * The UserData class is the one handling the communication between the server and the Users table in the database.
 * The class contains a set of methods for adding, retrieving, updating and removing user information from the table.
 * In order for the communication to be successful instances of DatabaseConnection and <code>java.sql.Connection</code>
 * classes are used.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class UserData
{
  DatabaseConnection db;
  private Connection connection;
  PreparedStatement pst;
  private DogData dogData;

  /**
   * A constructor setting up the communication between the class and the database
   * @throws SQLException if any SQL error occurs while setting up the connection
   */
  public UserData() throws SQLException
  {
    db = DatabaseConnection.getInstance();
    connection = db.getConnection();
    dogData = new DogData();
  }

  /**
   * A method for adding a new user's information to the table
   * @param user
   *        the new USer object whose information will be added to the database
   * @throws SQLException if any SQL related error occurs while executing the method
   */
  public void addUser(User user) throws SQLException
  {
    String sql = "INSERT INTO users(HANDLE,NAME,LASTNAME,imageURL,PASSWORD,EMAIL,BIRTHDAY,GENDER,BIO,USERTYPE,STATUS) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    PreparedStatement pst = connection.prepareStatement(sql);

    pst.setString(1, user.getHandle());
    pst.setString(2, user.getName());
    pst.setString(3, user.getLastname());
    pst.setBytes(4, user.getImageURL());
    pst.setString(5, user.getPassword());
    pst.setString(6, user.getEmail());
    pst.setDate(7, user.getBirthday());
    pst.setString(8, user.getGender());
    pst.setString(9,user.getBio());
    pst.setString(10,user.getUserType());
    pst.setString(11,user.getStatus());
    db.operation(pst);
    System.out.println("ADDED: " + user.getHandle());
  }

  /**
   * A method for removing a user's information from the table
   * @param user
   *        the User object that is to be removed from the table
   * @throws SQLException if any SQL related error occurs while executing the method
   */
  public void removeUser(User user) throws SQLException
  {
    String sql = "DELETE FROM users WHERE HANDLE =?";
    PreparedStatement pst = connection.prepareStatement(sql);
    pst.setString(1, user.getHandle());
    db.operation(pst);
    System.out.println("DELETED: " + user.getHandle());
  }

  /**
   * A method for updating a user's information in the table
   * @param user
   *        the modified User object
   * @throws SQLException if any SQL related error occurs while executing the method
   */
  public void editUser(User user) throws SQLException
  {
    String sql =
            "UPDATE USERS SET  NAME=?,LASTNAME=?,IMAGEURL=?,PASSWORD=?,EMAIL=?,BIRTHDAY=?,GENDER=?,BIO=?,USERTYPE=?,STATUS=?"
                    + "WHERE HANDLE=?";
    PreparedStatement pst = connection.prepareStatement(sql);

    pst.setString(11, user.getHandle());
    pst.setString(1, user.getName());
    pst.setString(2, user.getLastname());
    pst.setBytes(3, user.getImageURL());
    pst.setString(4, user.getPassword());
    pst.setString(5, user.getEmail());
    pst.setDate(6, user.getBirthday());
    pst.setString(7, user.getGender());
    pst.setString(8,user.getBio());
    pst.setString(9,user.getUserType());
    pst.setString(10,user.getStatus());
    db.operation(pst);
    System.out.println("EDITED: " + user.getHandle());
  }

  /**
   * A method for updating the user bio for the specified user
   * @param user
   *        the user with the new bio
   * @throws SQLException if any SQL related error occurs while executing the method
   */
  public void editBio(User user) throws SQLException{
    String sql = "UPDATE USERS SET BIO=? WHERE HANDLE=?";

    PreparedStatement pst = connection.prepareStatement(sql);

    pst.setString(1,user.getBio());
    pst.setString(2,user.getHandle());

    db.operation(pst);
    System.out.println("BIO ADDED: "+ user.getHandle()+": "+user.getBio());
  }

  /**
   * Getter for all the user information from the table
   * @return an ArrayList of User data type containing all the user information
   * in the table
   */
  public ArrayList<User> getUserList()
  {
    ArrayList<User> users = new ArrayList<>();
    try
    {
      Statement statement = connection.createStatement();
      connection.commit();

      ResultSet rs = statement.executeQuery("SELECT * FROM USERS;");

      User user;
      while (rs.next())
      {
        user = new User(rs.getString("HANDLE"), rs.getString("NAME"),
            rs.getString("LASTNAME"), rs.getBytes("IMAGEURL"),
            rs.getString("PASSWORD"), rs.getString("EMAIL"),
            rs.getDate("BIRTHDAY"), rs.getString("GENDER"),
            dogData.getDogsForUser(rs.getString("HANDLE")),rs.getString("BIO"),rs.getString("USERTYPE"),rs.getString("STATUS"));
        users.add(user);
      }
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return users;
  }

  /**
   * Getter for a User object based on its user handle
   * @param handle
   *        the handle of the user whose information will be retrieved from the table
   * @return an User object
   */
  public User getUserByHandle(String handle)
  {
    ArrayList<User> users = new ArrayList<>();
    User user=null;

    try
    {
      Statement statement = connection.createStatement();
      connection.commit();
      ResultSet rs = statement
          .executeQuery("SELECT * FROM USERS WHERE HANDLE='" + handle + "'");
      while (rs.next())
      {
      user = new User(rs.getString("HANDLE"), rs.getString("NAME"),
          rs.getString("LASTNAME"), rs.getBytes("IMAGEURL"),
          rs.getString("PASSWORD"), rs.getString("EMAIL"),
          rs.getDate("BIRTHDAY"), rs.getString("GENDER"),
          dogData.getDogsForUser(rs.getString("HANDLE")),rs.getString("BIO"),rs.getString("USERTYPE"),rs.getString("STATUS"));

        users.add(user);
        user=users.get(0);
      }
    }
    catch (Exception e)
    {

      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return user;
  }

}


