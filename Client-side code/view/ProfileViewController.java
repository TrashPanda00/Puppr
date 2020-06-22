package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Dog;
import model.Post;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import viewmodel.ProfileViewModel;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The ProfileViewController class handles the look and response to certain events of the profileView view. It contains
 * the information for creating the post cards and dog cards for the user's posts and owned dogs.
 *
 * @author Bogdan-Alexandru Mezei
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @version 1.0
 */
@SuppressWarnings("StatementWithEmptyBody")
public class ProfileViewController implements LocalListener<Object, Object> {

	private Region root;
	private ProfileViewModel viewModel;
	private ViewHandler viewHandler;

	@FXML
	private Label handle;
	@FXML
	private Label nameLabel;
	@FXML
	private VBox vBox;
	@FXML
	private VBox vBox1;
	@FXML
	private Rectangle portrait;
	@FXML
	private Label bioLabel;
	@FXML
	private Label likesLabel;
	@FXML
	private Label wallLabel;
	@FXML
	private Button block;

	private ArrayList<String> likedPosts;
	private ArrayList<String> likedDogPosts;
	private boolean mainUserPage;
	private String mainUser;
	private boolean isGuest;
	private boolean isAdmin;

	/**
	 * A method for initialising all the class variables and binding the view elements to
	 * the ViewModel properties.
	 *
	 * @param viewHandler      an instance of the ViewHandler class
	 * @param profileViewModel an instance of the ProfileViewModel class
	 * @param root             an instance of the current Region
	 */
	public void init(ViewHandler viewHandler, ProfileViewModel profileViewModel, Region root) {
		this.viewHandler = viewHandler;
		this.viewModel = profileViewModel;
		this.viewModel.addListener(this, "post");
		this.viewModel.addListener(this, "dog");
		this.viewModel.addListener(this, "otherUser");
		this.viewModel.addListener(this, "user");
		this.viewModel.addListener(this, "guest");
		this.root = root;
		likedPosts = viewModel.getAllLikes();
		likedDogPosts = viewModel.getAllDogLikes();

		handle.textProperty().bindBidirectional(viewModel.handleProperty());
		nameLabel.textProperty().bindBidirectional(viewModel.nameProperty());
		wallLabel.textProperty().bindBidirectional(viewModel.wallProperty());
		likesLabel.textProperty().bindBidirectional(viewModel.likesCountProperty());
		bioLabel.textProperty().bind(viewModel.bioProperty());
		mainUser = viewModel.getChangeableHandle();

		isGuest = viewModel.isGuest();
		isAdmin = viewModel.isAdmin();

		if (!isAdmin) {
			block.setText("BLOCK USER");
			block.setVisible(false);

		}

	}

	/**
	 * Getter for the Region type variable
	 *
	 * @return a reference to the <code>root</code> value
	 */
	public Region getRoot() {
		return root;
	}

	/**
	 * A method resetting the fields whenever the view is reopened
	 */
	public void reset() {
		mainUser = viewModel.getChangeableHandle();
		likedPosts = viewModel.getAllLikes();
		likedDogPosts = viewModel.getAllDogLikes();
		vBox.getChildren().clear();
		addLastPosts();
		addDogs();
		setPortrait();
	}

	/**
	 * A method for closing this view and opening the home view
	 */
	public void close() {
		viewHandler.openView("home");
	}

	/**
	 * A method for adding all the user's posts from latest to oldest
	 */
	public void addLastPosts() {
		if (viewModel.getHandle() != null) {
			ArrayList<Post> posts = viewModel.getPostsForUser(viewModel.getHandle().replace(" @", ""));
			int size = posts.size() - 1;

			for (int i = size; i >= 0; i--) {
				vBox.getChildren().add(makeCard(posts.get(i)));
			}
		}
	}

	/**
	 * A method for creating the cards containing the dog information for the user's dogs
	 *
	 * @param dog the Dog element, linked to the user, whose information will be added to the card
	 * @return a Pane element representing the dog card with all the information needed about the dog
	 * on it.
	 */
	public Pane pupprCard(Dog dog) {
		Rectangle photo = new Rectangle();
		photo.setHeight(120);
		photo.setWidth(120);
		byte[] imageurl = dog.getImageURL();
		if (imageurl != null) {
			Image img = new Image(new ByteArrayInputStream(imageurl));
			photo.setStroke(Color.web("#5B8266"));
			photo.setStrokeWidth(3);
			photo.setFill(new ImagePattern(img));
		} else
			photo.setFill(Color.web("#faead9"));

		Label dogname = new Label(dog.getName());
		dogname.getStyleClass().add("name");
		String verify2 = "";
		verify2 = dog.getDogId() + "/" + mainUser;
		ImageView bone;
		boolean found2 = false;
		if (likedDogPosts != null) {
			for (String like : likedDogPosts) {
				if (like.equals(verify2)) {
					found2 = true;
					break;
				}
			}
		}

		if (!found2)
			bone = new ImageView("resources/boneLike.png");
		else
			bone = new ImageView("resources/boneFilled.png");

		bone.setFitHeight(40);                                              //like button
		bone.setFitWidth(40);
		bone.setEffect(new ColorAdjust(0, 0, -0.14, -1));
		bone.setPickOnBounds(true);
		if (isGuest) {
			bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> guestAlert("You have to login to use like button "));

		} else {
			bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

				String verify = dog.getDogId() + "/" + mainUser;

				boolean found = false;
				if (likedDogPosts != null) {
					for (String like : likedDogPosts) {
						if (like.equals(verify)) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					viewModel.likeDogPost(dog.getDogId(), mainUser);
					vBox1.getChildren().clear();
					addDogs();
					likedDogPosts = viewModel.getAllDogLikes();
					bone.setImage(new Image("resources/boneFilled.png"));
					this.reset();
				}
			});
		}

		String likeString = "  " + dog.getLikes() + " likes";
		Label like = new Label(likeString);
		like.getStyleClass().add("userName");
		like.setMinWidth(Region.USE_PREF_SIZE);

		VBox likeSegment = new VBox(bone, like);
		likeSegment.setAlignment(Pos.TOP_RIGHT);
		likeSegment.setSpacing(5);
		likeSegment.setPadding(new Insets(40, 0, 0, 5));

		HBox end = null;
		if (mainUserPage) {
			Label edit = new Label("Edit");
			edit.getStyleClass().add("button");
			edit.setOnMouseClicked(e -> {
				viewModel.editDog(dog);
				viewHandler.openView("editDog");
			});

			Label delete = new Label("Delete");
			delete.getStyleClass().add("button");
			delete.setOnMouseClicked(e -> {
				viewModel.deleteDog(dog);
				addDogs();
			});
			end = new HBox(edit, delete);
			end.setSpacing(30);
			end.setPrefHeight(20);
			end.setPrefWidth(120);
			end.setAlignment(Pos.BOTTOM_CENTER);
			end.setPadding(new Insets(0, 0, 0, 60));
		}

		HBox part1;
		part1 = new HBox(photo, likeSegment);

		part1.setSpacing(40);
		part1.setPadding(new Insets(10, 0, 0, 20));

		Label text1 = new Label(dog.getInfo());
		text1.getStyleClass().add("post");
		text1.wrapTextProperty().set(true);
		text1.setMinHeight(Region.USE_PREF_SIZE);

		HBox dogBio = new HBox(text1);
		dogBio.setAlignment(Pos.CENTER_LEFT);
		dogBio.setMinHeight(Region.USE_PREF_SIZE);
		dogBio.setPrefWidth(190);
		dogBio.setPadding(new Insets(0, 0, 0, 20));

		dogname.setPadding(new Insets(0, 0, 0, 20));
		VBox part2;
		if (end != null)
			part2 = new VBox(part1, dogname, dogBio, end);
		else
			part2 = new VBox(part1, dogname, dogBio);
		part2.setMinHeight(Region.USE_PREF_SIZE);
		part2.setPrefWidth(190);
		part2.setSpacing(5);
		part2.setPadding(new Insets(10, 0, 10, 0));

		Pane pane = new Pane(part2);
		pane.setMinHeight(Region.USE_PREF_SIZE + 100);
		pane.setPrefWidth(190);
		pane.getStyleClass().add("mainFxmlClass");

		return pane;
	}

	/**
	 * A method for creating a post card with all the needed post information from owner to
	 * the post text and functional event buttons.
	 *
	 * @param post the Post element being added to the card
	 * @return a Pane element representing the post card with all the post's information present
	 * on it.
	 */
	public Pane makeCard(Post post) {
		Circle portrait = new Circle();
		portrait.setRadius(40);

		User user = viewModel.getUserByHandle(post.getHandle());
		byte[] imageurl = user.getImageURL();
		if (imageurl != null) {
			Image img = new Image(new ByteArrayInputStream(imageurl));
			portrait.setStroke(Color.web("#5B8266"));
			portrait.setStrokeWidth(3);
			portrait.setFill(new ImagePattern(img));
		}



		String verify2 = "";
		try
		{
			verify2 = post.getPostId() + "/" + handle.getText().replace(" @", "");
		}
		catch(Exception e)
		{

		}
		ImageView bone;
		boolean found2 = false;
		if(likedPosts!=null) {
			for (String like : likedPosts) {
				if (like.equals(verify2)) {
					found2 = true;
					break;
				}
			}
		}

		if(!found2)
			bone = new ImageView("resources/boneLike.png");
		else
			bone = new ImageView("resources/boneFilled.png");

		bone.setFitHeight(45);                                              //like button
		bone.setFitWidth(45);
		bone.setEffect(new ColorAdjust(0, 0, -0.14, -1));
		bone.setPickOnBounds(true);


		if (isGuest) {
			bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				ButtonType guest = new ButtonType("Continue as a guest", ButtonBar.ButtonData.CANCEL_CLOSE);
				ButtonType login = new ButtonType("Go to login page", ButtonBar.ButtonData.CANCEL_CLOSE);
				Alert alert = new Alert(Alert.AlertType.NONE,
				                        "You have to login to use like button ",
				                        login,
				                        guest);

				Optional<ButtonType> result = alert.showAndWait();

				if(result.get().equals(login)){
					viewHandler.openView("login");
				}
			});

		} else {
			bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				String verify = post.getPostId() + "/" + handle.getText().replace(" @", "");
				boolean found = false;
				if(likedPosts!=null) {
					for (String like : likedPosts) {
						if (like.equals(verify)) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					viewModel.likePost(post.getPostId(), handle.getText().replace(" @", ""));
					vBox.getChildren().clear();
					addLastPosts();
					likedPosts = viewModel.getAllLikes();
					bone.setImage(new Image("resources/boneFilled.png"));
					this.reset();
				}
			});
		}


		ImageView comment = new ImageView("resources/comment.png");
		comment.setFitHeight(45);                                                //comment button
		comment.setFitWidth(45);
		comment.setEffect(new ColorAdjust(0, 0, -0.14, -1));
		comment.setPickOnBounds(true);

		if (isGuest) {

			comment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

				ButtonType guest = new ButtonType("Continue as a guest", ButtonBar.ButtonData.CANCEL_CLOSE);
				ButtonType login = new ButtonType("Go to login page", ButtonBar.ButtonData.CANCEL_CLOSE);
				Alert alert = new Alert(Alert.AlertType.NONE,
				                        "You have to login to leave a comment.", login, guest);

				Optional<ButtonType> result = alert.showAndWait();

				if(result.get().equals(login)){
					viewHandler.openView("login");
				}

			});


		} else {
			comment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				viewModel.setComment(post);
				viewHandler.openView("createComment");
			});
		}


		VBox left = new VBox(portrait, bone, comment);
		left.setAlignment(Pos.TOP_CENTER);
		left.setSpacing(0);


		User user1 = viewModel.getUserByHandle(post.getHandle());
		Label username = new Label(user1.getName() + " " + user1.getLastname() + " ●");                //username
		username.getStyleClass().add("name");

		Label time = new Label(" " + post.getTimePosted().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + post.getTimePosted().format(
				DateTimeFormatter.ofPattern("HH:mm:ss")));               //time
		time.getStyleClass().add("time");
		HBox userTime = new HBox(username, time);
		userTime.setAlignment(Pos.CENTER_LEFT);


		String aux = "@";
		aux = aux + post.getHandle() + " - " + post.getLikes() + " likes and " + post.getComments().size() + " comments";         //handle, likes and comments
		Label handle1 = new Label(aux);
		handle1.getStyleClass().add("userName");


		Label text1 = new Label(post.getText());
		text1.getStyleClass().add("post");                                     //post itself
		text1.setPrefHeight(120);
		text1.setPrefWidth(434);
		text1.wrapTextProperty().set(true);

		Rectangle photo = new Rectangle();
		if (post.getImageURL() != null) {
			photo.setWidth(400);
			photo.setHeight(340);
			photo.setStroke(Color.web("#5B8266"));
			photo.setStrokeWidth(3);                                                                                            //image
			javafx.scene.image.Image img2 = new javafx.scene.image.Image(new ByteArrayInputStream(post.getImageURL()));
			photo.setFill(new ImagePattern(img2));
		}

		VBox right;
		Label show = new Label("Show");
		show.setPrefWidth(60);                                                         //show
		show.getStyleClass().add("button");
		show.setOnMouseClicked(e -> {
			viewModel.openPost(post.getPostId());
			viewHandler.openView("viewPost");
		});

		if (isAdmin) {
			Label edit = new Label("Edit");
			edit.getStyleClass().add("button");
			edit.setPrefWidth(60);
			edit.setOnMouseClicked(e -> {
				viewModel.editPost(post);
				viewHandler.openView("editPost");
			});
			Label delete = new Label("Delete");
			delete.setPrefWidth(60);
			delete.getStyleClass().add("button");
			delete.setOnMouseClicked(e -> {
				viewModel.deletePost(post);
				vBox.getChildren().clear();
				addLastPosts();
			});


			HBox end = new HBox(show, edit, delete);
			end.setSpacing(10);
			end.setAlignment(Pos.BOTTOM_RIGHT);
			end.setPadding(new Insets(4, 0, 10, 0));

			right = new VBox(userTime, handle1, text1, photo, end);
			right.setSpacing(10);
			left.setSpacing(10);
		} else {
			HBox end = new HBox(show);
			end.setAlignment(Pos.BOTTOM_RIGHT);
			right = new VBox(userTime, handle1, text1, photo, end);
			right.setSpacing(10);
			left.setSpacing(27);
		}

		HBox hBox = new HBox(left, right);
		hBox.setSpacing(5);
		hBox.setPadding(new Insets(10, 17, 10, 20));

		Pane pane = new Pane(hBox);
		pane.setPrefWidth(574);
		if (post.getImageURL() != null)
			pane.setPrefHeight(610);
		else
			pane.setPrefHeight(274);


		pane.getStyleClass().add("mainFxmlClass");

		return pane;
	}

	/**
	 * A method adding all the user's dogs as dog cards to the view
	 */
	public void addDogs() {
		ArrayList<Dog> dogs = viewModel.getDogsForUser(viewModel.getHandle().replace(" @", ""));
		vBox1.getChildren().clear();
		for (Dog dog : dogs) {
			vBox1.getChildren().add(pupprCard(dog));
		}
	}

	/**
	 * A method setting the user's profile picture
	 */
	private void setPortrait() {
		User user = viewModel.getUserByHandle(viewModel.getHandle().replace(" @", ""));
		byte[] imageurl = user.getImageURL();
		if (imageurl != null) {
			javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(imageurl));
			portrait.setFill(new ImagePattern(img));
		}
	}

	/**
	 * A method being called every time a new subject property changes. The method reacts
	 * differently based on the property name
	 *
	 * @param event the even that was fired by the subject and listened here
	 */
	@Override
	public void propertyChange(ObserverEvent<Object, Object> event) {
		Platform.runLater(() -> {

			switch (event.getPropertyName()) {

				case "post":
					vBox.getChildren().clear();
					addLastPosts();
					setPortrait();
					break;
				case "dog":
					vBox1.getChildren().clear();
					addDogs();
					break;
				case "otherUser":
					mainUser = event.getValue1().toString();

					if (mainUser.equals("guest")) {
						isGuest = true;
						mainUserPage=false;
						block.setVisible(false);
					} else {
						isGuest = false;

						if (mainUser.equals(viewModel.getHandle().replace(" @", ""))) {
							mainUserPage = true;
						} else {
							mainUserPage = false;
						}

						User user = viewModel.getUserByHandle(viewModel.getHandle().replace(" @", ""));
						if (viewModel.getUserByHandle(mainUser).getUserType().equals("admin")) {
							block.setVisible(true);
							if (user.getUserType().equals("user")) {
								if (user.getStatus().equalsIgnoreCase("unblocked")) {
									block.setText("BLOCK USER");
								} else {
									block.setText("UNBLOCK USER");
								}
							} else
								block.setVisible(false);
						} else
							block.setVisible(false);
					}

					setPortrait();
					vBox.getChildren().clear();
					addLastPosts();
					vBox1.getChildren().clear();
					addDogs();
					break;
				case "user":
					isGuest = false;
					mainUser = event.getValue2() + "";
					mainUserPage = true;
					if (viewModel.getUserByHandle(mainUser).getUserType().equalsIgnoreCase("admin")) {
						isAdmin = true;
					} else {
						isAdmin = false;
					}
					block.setVisible(false);
					setPortrait();
					vBox.getChildren().clear();
					addLastPosts();
					vBox1.getChildren().clear();
					addDogs();
					break;
				case "guest":
					isGuest = (boolean) event.getValue2();
					isAdmin = false;
					block.setVisible(false);
					mainUserPage = false;
					setPortrait();
					vBox.getChildren().clear();
					addLastPosts();
					vBox1.getChildren().clear();
					addDogs();
					break;
				case "admin":
					isGuest = false;
					isAdmin = (boolean) event.getValue2();
					if (!isAdmin) {
						block.setVisible(false);
					}
					block.setVisible(true);
					break;
			}
		});
	}

	/**
	 * A method for admin's allowing them to block or unblock any user. Blocking will refrain the user from
	 * logging into the app until they are unblocked.
	 */
	public void blockUser() {
		User user = viewModel.getUserByHandle(viewModel.getHandle().replace(" @", ""));
		if (user.getUserType().equals("user")) {
			if (user.getStatus().equalsIgnoreCase("unblocked")) {
				viewModel.blockUser();
				block.setText("UNBLOCK USER");
			} else {
				viewModel.blockUser();
				block.setText("BLOCK USER");
			}
		}
	}

	/**
	 * A method showing an alert is a guest tries to do any user functions like commenting on a post.
	 *
	 * @param message the alert message shown to the guest.
	 */
	public void guestAlert(String message) {
		ButtonType guest = new ButtonType("Continue as a guest", ButtonBar.ButtonData.CANCEL_CLOSE);
		ButtonType login = new ButtonType("Go to login page", ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(Alert.AlertType.NONE, message, login, guest);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(login) == guest) {
			//do nothing
		} else {
			viewHandler.openView("login");
		}
	}
}