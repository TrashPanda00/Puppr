package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.ImageConverter;
import model.LocalModel;
import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The SignupViewModel class handles the logic behind the signup view
 *
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class SignupViewModel
{

	private LocalModel model;
	private StringProperty gender;
	private StringProperty handle;
	private ObjectProperty<LocalDate> birthday;

	private StringProperty email;
	private StringProperty password;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty bio;
	private byte[] imageurl;

	/**
	 * A constructor setting the local variables
	 *
	 * @param model an instance of the LocalModel interface
	 */
	public SignupViewModel(LocalModel model)
	{
		this.model = model;
		gender = new SimpleStringProperty();
		handle = new SimpleStringProperty();
		birthday = new SimpleObjectProperty<>();
		email = new SimpleStringProperty();
		password = new SimpleStringProperty();
		firstName = new SimpleStringProperty();
		lastName = new SimpleStringProperty();
		bio = new SimpleStringProperty();
		imageurl = null;
	}

	/**
	 * Getter for the bio property
	 *
	 * @return a reference to the bio String property
	 */
	public StringProperty bioProperty() {
		return bio;
	}

	/**
	 * Getter for the password property
	 *
	 * @return a reference to the password String property
	 */
	public StringProperty passwordProperty()
	{
		return password;
	}

	/**
	 * Getter for the user's birthday
	 *
	 * @return a reference to the user's birthday
	 */
	public LocalDate getBirthday()
	{
		return birthday.get();
	}

	/**
	 * Getter for the birthday property
	 *
	 * @return a reference to the birthday property
	 */
	public ObjectProperty<LocalDate> birthdayProperty()
	{
		return birthday;
	}

	/**
	 * Getter for the email property
	 *
	 * @return a reference to the email property
	 */
	public StringProperty emailProperty()
	{
		return email;
	}

	/**
	 * Getter for the first name property
	 *
	 * @return a reference to the first name String property
	 */
	public StringProperty firstNameProperty()
	{
		return firstName;
	}

	/**
	 * Getter for the gender property
	 *
	 * @return a reference to the gender String property
	 */
	public StringProperty genderProperty()
	{
		return gender;
	}

	/**
	 * Getter for the handle property
	 *
	 * @return a reference to the user's handle property
	 */
	public StringProperty handleProperty()
	{
		return handle;
	}

	/**
	 * Getter for the last name property
	 *
	 * @return a reference to the user's last name property
	 */
	public StringProperty lastNameProperty()
	{
		return lastName;
	}

	/**
	 * Setter for the user's gender
	 *
	 * @param gender a reference to the user's gender attribute
	 */
	public void setGender(String gender)
	{
		this.gender.set(gender);
	}

	/**
	 * A method for signing up a new user and sending the information to the model
	 */
	public void signUpUser()
	{
		LocalDate localDate = birthday.get();
		Date date = Date.valueOf(localDate);

		MessageDigest digest = null;
		try
		{
			digest = MessageDigest.getInstance("SHA-256");
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		assert digest != null;
		byte[] encodedhash = digest.digest(password.get().getBytes(StandardCharsets.UTF_8));
		String finalhash = bytesToHex(encodedhash);

		if(imageurl == null)
		{
			try
			{
				imageurl = ImageConverter.ImageToByte(new File("src/resources/default-user.png"));
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		User user = new User(handle.get(), firstName.get(), lastName.get(), imageurl, finalhash, email.get(), date, gender.get(), null, bio.get(), "user", "unblocked");
		model.addUser(user);
	}

	/**
	 * Setter for the image url
	 *
	 * @param imageurl the new bye information for the imageurl variable
	 */
	public void setImageurl(byte[] imageurl)
	{
		this.imageurl = imageurl;
	}

	/**
	 * Getter for the image url
	 *
	 * @return a reference to the byte information of the user's profile picture
	 */
	public byte[] getImageurl()
	{
		return imageurl;
	}

	/**
	 * A method for converting the hex String to a normal string
	 * @param hash the byte information of the hashed password
	 * @return the String value of the hashed password
	 */
	private static String bytesToHex(byte[] hash)
	{
		StringBuffer hexString = new StringBuffer();
		for(int i = 0; i < hash.length; i++)
		{
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * Getter for all the signed up users
	 * @return an ArrayList of User data type containing the information related to all the users
	 */
	public ArrayList<User> getAllUsers(){
		return model.getUserList();
	}


}
