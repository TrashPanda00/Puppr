package database;

import model.Dog;

import java.sql.*;
import java.util.ArrayList;

/**
 * The DogData class handles the communication between the server and the Dogs table in the database.
 * The class contains a list of methods that add, remove or update dog information form the table.
 * Communication with the database is made possible using an instance of the DatabaseConnection class
 * and the java.sql.Connection class.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class DogData
{
  private  DatabaseConnection db;
  private PreparedStatement pst;
  private Connection connection;


  /**
   * A constructor setting up the connection
   * @throws SQLException if any error occurs while setting up the connection
   */
  public DogData() throws SQLException
  {
    db= DatabaseConnection.getInstance();
    connection=db.getConnection();
  }

  /**
   * A method for adding a new Dog object to the table
   * @param dog
   *        the dog object that is to be added to the table
   * @throws SQLException if any error occurs while executing the method
   */
  public void addDog(Dog dog) throws SQLException
  {
    String sql = "INSERT INTO DOGS (DOG_NAME,IMAGE_URL,INFO,DOG_OWNER,LIKES) VALUES (?,?,?,?,?);";
    PreparedStatement pst=connection.prepareStatement(sql);
    pst.setString(1,dog.getName());
    pst.setBytes(2, dog.getImageURL());
    pst.setString(3,dog.getInfo());
    pst.setString(4,dog.getOwnerName());
    pst.setInt(5,dog.getLikes());
    //db.add(pst);
    db.operation(pst);
    System.out.println("ADDED: "+dog.getName());
  }

  /**
   * A method for updating a dog's information in the table
   * @param dog
   *        the modified dog object
   * @throws SQLException if any error occurs while executing the method
   */
  public void editDog(Dog dog) throws SQLException
  {
    String sql = "UPDATE DOGS SET DOG_NAME=?, IMAGE_URL=?,INFO=?, DOG_OWNER=?,LIKES=? WHERE DOG_ID=?";
    PreparedStatement pst = connection.prepareStatement(sql);
    pst.setString(1,dog.getName());
    pst.setBytes(2, dog.getImageURL());
    pst.setString(3,dog.getInfo());
    pst.setString(4,dog.getOwnerName());
    pst.setInt(5,dog.getLikes());
    pst.setInt(6,dog.getDogId());
    db.operation(pst);
    System.out.println("EDITED: "+dog.getName());
  }

  /**
   * A method for removing a dog from the database
   * @param dog
   *        the Dog object that is to be removed from the database
   * @throws SQLException if any error occurs while executing the method
   */
  public void removeDog(Dog dog) throws SQLException
  {
    String sql = "DELETE FROM DOGS WHERE DOG_ID =?";
    pst = connection.prepareStatement(sql);
    pst.setInt(1,dog.getDogId());
    // db.delete(pst);
    db.operation(pst);
    System.out.println("DELETED: "+dog.getName());
  }

  /**
   * Getter for the list of dogs owner by a specific user
   * @param handle
   *        the handle of the dog owner
   * @return an ArrayList of Dog data type containing all the dogs owned by the
   * specified user.
   */
  public ArrayList<Dog> getDogsForUser(String handle){
    ArrayList<Dog> dogs=new ArrayList<>();

    try
    {
      Statement statement = connection.createStatement();
      connection.commit();

      ResultSet rs = statement.executeQuery("SELECT * FROM DOGS WHERE DOG_OWNER='"+handle+"'ORDER BY DOG_NAME ASC");

      Dog dog;
      while (rs.next())
      {
        dog=new Dog(rs.getInt("DOG_ID"),rs.getString("DOG_NAME"),
                    rs.getBytes("IMAGE_URL"),rs.getString("INFO"),rs.getString("DOG_OWNER"), rs.getInt("LIKES"));
        dogs.add(dog);
      }
    }
    catch(Exception e){
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    return dogs;
  }

  /**
   * Getter for the complete list of dogs in descending order of their number of likes.
   * This getter is used mostly for the hall of fame.
   * @return an ArrayList of Dog data type containing all the dog information in the table
   */
  public ArrayList<Dog> getDogList(){
    ArrayList<Dog> dogs=new ArrayList<>();

    try
    {
      Statement statement = connection.createStatement();
      connection.commit();

      ResultSet rs = statement.executeQuery("SELECT * FROM DOGS ORDER BY LIKES DESC");

      Dog dog;
      while (rs.next())
      {
        dog=new Dog(rs.getInt("DOG_ID"),rs.getString("DOG_NAME"),
                    rs.getBytes("IMAGE_URL"),rs.getString("INFO"),rs.getString("DOG_OWNER"), rs.getInt("LIKES"));
        dogs.add(dog);
      }
    }
    catch(Exception e){
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    return dogs;
  }


  /**
   * A method for incrementing the <code>likes</code> of a Dog object by 1 in the table
   * @param dogId
   *        the id of the specified Dog object
   * @throws SQLException if any error occurs while executing the method
   */
  public void addLikeToDog(int dogId) throws SQLException {

    Dog dog=getDogById(dogId);
    int likes = dog.getLikes();
    String sql = "UPDATE DOGS SET LIKES=?  WHERE DOG_ID=?";
    pst = connection.prepareStatement(sql);
    pst.setInt(1, ++likes);
    pst.setInt(2, dog.getDogId());
    db.operation(pst);
    System.out.println("LIKED");
  }

  /**
   * Getter for a specified DOg object. If any errors occur during the execution of this method,
   * the error message is printed out on the console.
   * @param dogId
   *        the id of the dog whose information will be retrieved from the table
   * @return a Dog object with the same id as the specified <code>dogId</code>
   */
  public Dog getDogById(int dogId) {

    ArrayList<Dog> dogs=new ArrayList<>();
    Dog dog=null;

    try
    {
      Statement statement = connection.createStatement();
      connection.commit();

      ResultSet rs = statement.executeQuery("SELECT * FROM DOGS WHERE DOG_ID="+dogId);

      while (rs.next())
      {
        dog=new Dog(rs.getInt("DOG_ID"),rs.getString("DOG_NAME"),
                    rs.getBytes("IMAGE_URL"),rs.getString("INFO"),rs.getString("DOG_OWNER"), rs.getInt("LIKES"));
        dogs.add(dog);
        dog=dogs.get(0);
      }
    }
    catch(Exception e){
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    return dog;
  }

}
