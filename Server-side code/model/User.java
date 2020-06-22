package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  The User class contains all the needed information about a user of the Puppr app.
 *  This includes the unique handle, the full name, birthday and list of dogs
 *  associated with that user.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class User implements Serializable {
  private String handle;
  private String name;
  private String lastname;
  private byte[] imageURL;
  private String password;
  private String email;
  private Date birthday;
  private String gender;
  private ArrayList<Dog> dogList;
  private String bio;
  private String userType;
  private String status;

  /**
   * A constructor used to set every attribute of the User object
   * @param handle
   *        the user handle; an unique identifier between all the users.
   * @param name
   *        the first name of the user
   * @param lastname
   *        the last name (family name) of the user
   * @param imageURL
   *        the byte information of the user's profile picture
   * @param password
   *        the password for the user account
   * @param email
   *        the user's email
   * @param birthday
   *        the birthday of the user
   * @param gender
   *        the user's gender
   * @param dogList
   *        the list of all the dog's the user has linked to their profile. Can be <code>null</code>
   * @param bio
   *        a short text added by the user about them or the their page. Can also be <code>null</code>
   * @param userType
   *        indicates if the person has an admin role for the app or is a simple app user
   * @param status
   *        indicates if the user is blocked by the admin
   */
  public User(String handle, String name, String lastname, byte[] imageURL, String password,
              String email, Date birthday, String gender, ArrayList<Dog> dogList, String bio, String userType, String status) {
    this.handle = handle;
    this.name = name;
    this.lastname = lastname;
    this.imageURL = imageURL;
    this.password = password;
    this.email = email;
    this.birthday = birthday;
    this.gender = gender;
    this.dogList = dogList;
    this.bio = bio;
    this.userType = userType;
    this.status=status;
  }

  /**
   * Getter for the handle attribute
   * @return a reference to the handle of the current user
   */
  public String getHandle() {
    return handle;
  }

  /**
   * Getter for the first name of the user
   * @return a reference to the first name value of the User object
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the user's profile picture
   * @return a byte array (<code>byte[]</code>) representing the byte information of the user's
   * profile picture. Can be <code>null</code>
   */
  public byte[] getImageURL() {
    return imageURL;
  }

  /**
   * Getter for the password attribute of the user
   * @return a reference to the password value of the User object
   */
  public String getPassword() {
    return password;
  }

  /**
   * Getter for the email attribute of the user
   * @return a reference to the email value of the User object
   */
  public String getEmail() {
    return email;
  }

  /**
   * Getter for the birthday attribute of the user
   * @return a reference to the birthday value of the User object
   */
  public Date getBirthday() {
    return birthday;
  }

  /**
   * Getter for the gender attribute of the user
   * @return a reference to the gender value of the User object
   */
  public String getGender() {
    return gender;
  }

  /**
   * Getter for the list of dogs linked to the user's profile
   * @return an ArrayList of data type Dog containing all the Dog object that the user has linked to it.
   * This list can be <code>null</code>
   */
  public ArrayList<Dog> getDogList() {
    return dogList;
  }

  /**
   * Getter for the last name of the user
   * @return a reference to the last name value of the User object
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Getter for the bio of the user
   * @return a reference to the bio value of the User object
   */
  public String getBio() {
    return bio;
  }

  /**
   * Setter for the bio of the user
   * @param bio
   *        a String value representing the new bio of the user
   */
  public void setBio(String bio) {
    this.bio = bio;
  }

  /**
   * Getter for the user type
   * @return a String value used to differentiate between normal user and an admin.
   */
  public String getUserType() {
    return userType;
  }

  /**
   * Getter for the status of the user
   * @return a String value "blocked" or "unblocked" signalling if the user is
   * a guest, respectively logged in.
   */
  public String getStatus() {
    return status;
  }

  /**
   * A String representation of the User object. The attributes of the user are separated by commas
   * and encapsulated in a set of curly braces.
   * Example: "User{handle='andrew01', name='Andrew', lastname='Smith', imageURL=[B@4873020,
   *          password='asd323', email='andrew01@gmail.com', birthday=1999-12-03, gender='male',
   *          dogList=[], bio='', userType='user'}"
   * @return a String representation of the User object
   */


  @Override public String toString()
  {
    return "User{" + "handle='" + handle + '\'' + ", name='" + name + '\''
            + ", lastname='" + lastname + '\'' + ", imageURL=" + Arrays
            .toString(imageURL) + ", password='" + password + '\'' + ", email='"
            + email + '\'' + ", birthday=" + birthday + ", gender='" + gender + '\''
            + ", dogList=" + dogList + ", bio='" + bio + '\'' + ", userType='"
            + userType + '\'' + '}';
  }
}
