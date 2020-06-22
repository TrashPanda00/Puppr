package model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Dog is a class which holds the needed information about a dog such as
 * the unique dog id, name, name of the owner, etc.
 * The class implements <code>Serializable</code> in order to be send and received from the Server.
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
public class Dog implements Serializable
{
  private int dogId;
  private String name;
  private byte[] imageURL;
  private String info;
  private String ownerName;
  private int likes;

  /**
   * A constructor used to construct a new Dog object setting the name, imageUrl,
   * dog info, owner name and the number of likes.
   * @param name
   *        the name of the dog which cannot be <code>null</code>
   * @param imageURL
   *        the byte information describing an image with the dog
   * @param info
   *        info about the dog/dog bio
   * @param ownerName
   *        the name of the owner
   * @param likes
   *        the number of likes the dog has on the platform
   */
  public Dog(String name, byte[] imageURL, String info, String ownerName,int likes)
  {
    this.name = name;
    this.imageURL = imageURL;
    this.info = info;
    this.ownerName = ownerName;
    this.likes=likes;
  }

  /**
   * Another constructor which also sets the <code>dogID</code>, the
   * unique identifier for each dog.
   * This constructor is mostly used to retrieve a dog object from the server/database
   * @param dogId
   *        the dog id used to identify a dog object from another
   */
  public Dog(int dogId, String name, byte[] imageURL, String info,
             String ownerName,int likes)
  {
    this.dogId = dogId;
    this.name = name;
    this.imageURL = imageURL;
    this.info = info;
    this.ownerName = ownerName;
    this.likes=likes;
  }

  /**
   * Getter for the dog id
   * @return the <code> dogId </code> value for the current Dog object
   */
  public int getDogId()
  {
    return dogId;
  }

  /**
   * Getter for the dog name
   * @return a reference to the <code>name</code> of the current Dog object
   */
  public String getName()
  {
    return name;
  }

  /**
   * Getter for the <code>byte[]</code> (byte array) describing a photo
   * @return a reference to the byte information of a photo about/with a dog
   */
  public byte[] getImageURL()
  {
    return imageURL;
  }

  /**
   * Getter for the dog bio
   * @return a reference to the <code>info</code> of the current Dog object
   */
  public String getInfo()
  {
    return info;
  }

  /**
   * Getter for the owner handle
   * @return a reference to the <code>ownerName</code> value for the current Dog object
   */
  public String getOwnerName()
  {
    return ownerName;
  }

  /**
   * Getter for the number of likes for a Dog
   * @return the number of likes for the current Dog object
   */
  public int getLikes()
  {
    return likes;
  }

  /**
   * A String representation of the Dog object
   * @return a <code>String</code> with the dog attributes separated by commas and
   *         encompassed in a set of curly braces {}
   *         Example: "Dog{dogId=1, name='Bruno', imageUrl=324f8a4g, info='My fluffy boy', ownerName='John', likes=54}
   */
  @Override public String toString()
  {
    return "Dog{" + "dogId=" + dogId + ", name='" + name + '\'' + ", imageURL="
            + Arrays.toString(imageURL) + ", info='" + info + '\'' + ", ownerName='"
            + ownerName + '\'' + ", likes=" + likes + '}';
  }
}
