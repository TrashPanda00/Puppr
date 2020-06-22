package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The DatabaseConnection class is the one that connects the server to the database. The class uses the Singleton
 * design patter to ensure that there is only one connection to the same database all around the server.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class DatabaseConnection
{

  private static DatabaseConnection instance;
  private Connection connection;
  private String url = "jdbc:postgresql://localhost/puppr";
  private String username = "postgres";
  private String password = "redacted";

  /**
   * A constructor connecting the class to the SEPDB database and setting the Auto Commit to false
   * in order to ensure that no faulty data is sent to the database if anything fails in the process.
   * @throws SQLException if any SQL error occurs while setting up the database connection.
   */
  private DatabaseConnection() throws SQLException
  {
    try
    {
      Class.forName("org.postgresql.Driver");
      this.connection = DriverManager.getConnection(url, username, password);
      connection.setAutoCommit(false);
      System.out.println("Database connected");
    }
    catch (ClassNotFoundException ex)
    {
      System.out.println("Database Connection Creation Failed : " + ex.getMessage());
    }
  }

  /**
   * Getter for the connection
   * @return a reference to the <code>java.sql.Connection</code> instance
   */
  public Connection getConnection()
  {
    return connection;
  }

  /**
   * Getter for the class instance. The static method is part of the Singleton pattern.
   * @return the same DatabaseConnection instance every time
   * @throws SQLException if any SQL error occurs while executing the method.
   */
  public static DatabaseConnection getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new DatabaseConnection();
    }
    else if (instance.getConnection().isClosed())
    {
      instance = new DatabaseConnection();
    }

    return instance;
  }

  /**
   * A method for executing an operation in the database and committing the changes. If
   * any error occurs while executing the method the error message is printed out on the console.
   * @param pst the statement that is to be executed in the database
   */
  public void operation(PreparedStatement pst)
  {
    try
    {
      pst.executeUpdate();
      pst.close();
      connection.commit();
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }

  }

}
