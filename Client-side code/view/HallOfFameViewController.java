package view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Dog;
import model.User;
import viewmodel.HallOfFameViewModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * HallOfFameViewController class handles the look and response to certain events of the hallOfFameView
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class HallOfFameViewController {

    private Region root;
    private HallOfFameViewModel viewModel;
    private ViewHandler viewHandler;
    private ArrayList<Dog> dogs;
    @FXML
    private Rectangle firstPhoto;
    @FXML
    private Rectangle secondPhoto;
    @FXML
    private Rectangle thirdPhoto;
    @FXML
    private Label firstName;
    @FXML
    private Label secondName;
    @FXML
    private Label thirdName;
    @FXML
    private Label firstOwner;
    @FXML
    private Label secondOwner;
    @FXML
    private Label thirdOwner;
    @FXML
    private VBox vBox1;

    /**
     * A method for initialising all the class variables and setting the podium
     *
     * @param viewHandler         an instance of the ViewHandler class
     * @param hallOfFameViewModel an instance of the HallOfFameViewModel class
     * @param root                an instance of the current Region
     */
    public void init(ViewHandler viewHandler, HallOfFameViewModel hallOfFameViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = hallOfFameViewModel;
        this.root = root;
        dogs = viewModel.allDogs();
        if (dogs != null)
            if (dogs.size() > 0)
                setDogs();
        addRunnersUp();
    }

    /**
     * A method for resetting the fields when the view is reopened
     */
    public void reset() {
        vBox1.getChildren().clear();
        dogs = viewModel.allDogs();
        if (dogs != null)
            if (dogs.size() > 0)
                setDogs();
        addRunnersUp();
    }

    /**
     * A method for closing this view and opening the home view
     */
    public void close() {
        viewHandler.openView("home");
    }

    /**
     * Getter for the Region variable
     *
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method for setting the top three dogs based on the number of likes they have and linking
     * the owner profile to them.
     */
    public void setDogs() {
        int size = 0;
        if (dogs != null)
            size = dogs.size();
        if (size >= 3) {
            if (dogs.get(0).getImageURL() != null)
                firstPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(0).getImageURL()))));
            if (dogs.get(1).getImageURL() != null)
                secondPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(1).getImageURL()))));
            if (dogs.get(2).getImageURL() != null)
                thirdPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(2).getImageURL()))));

            firstName.setText(dogs.get(0).getName());
            secondName.setText(dogs.get(1).getName());
            thirdName.setText(dogs.get(2).getName());
            firstOwner.setText("@ " + dogs.get(0).getOwnerName());
            secondOwner.setText("@ " + dogs.get(1).getOwnerName());
            thirdOwner.setText("@ " + dogs.get(2).getOwnerName());
        } else if (size == 2) {
            if (dogs.get(0).getImageURL() != null)
                firstPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(0).getImageURL()))));
            if (dogs.get(1).getImageURL() != null)
                secondPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(1).getImageURL()))));
            firstName.setText(dogs.get(0).getName());
            secondName.setText(dogs.get(1).getName());
            firstOwner.setText("@ " + dogs.get(0).getOwnerName());
            secondOwner.setText("@ " + dogs.get(1).getOwnerName());
        } else if (size == 1) {
            if (dogs.get(0).getImageURL() != null)
                firstPhoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(dogs.get(0).getImageURL()))));
            firstName.setText(dogs.get(0).getName());
            firstOwner.setText("@ " + dogs.get(0).getOwnerName());
        }
    }

    /**
     * A method for viewing the profile for the owner of the 1st placed dog
     */
    public void viewOwner1() {
        if (dogs != null) {
            viewModel.openUserProfile(dogs.get(0).getOwnerName());
            viewHandler.openView("profile");
        }
    }

    /**
     * A method for viewing the profile for the owner of the 2nd placed dog
     */
    public void viewOwner2() {
        if (dogs != null)
            if (dogs.size() > 1) {
                viewModel.openUserProfile(dogs.get(1).getOwnerName());
                viewHandler.openView("profile");
            }
    }

    /**
     * A method for viewing the profile for the owner of the 3rd placed dog
     */
    public void viewOwner3() {
        if (dogs != null)
            if (dogs.size() > 2) {
                viewModel.openUserProfile(dogs.get(2).getOwnerName());
                viewHandler.openView("profile");
            }
    }


    /**
     * A method adding the 4th up to 10th placed dogs based on their likes
     */
    public void addRunnersUp() {
        if (dogs != null) {
            if (dogs.size() <= 10) {
                for (int i = 3; i < dogs.size(); i++) {
                    Pane pane = makeRunnerUpCard(dogs.get(i), i + 1);
                    vBox1.getChildren().add(pane);
                }
            } else {
                for (int i = 3; i < 10; i++) {
                    Pane pane = makeRunnerUpCard(dogs.get(i), i + 1);
                    vBox1.getChildren().add(pane);
                }
            }
        }
    }

    /**
     * A method creating the runner up cards with the owner and dog information, as well
     * as their place in the Hall of Fame
     *
     * @param dog   the Dog object part of the Hall of Fame
     * @param place the dog's position in the Hall of Fame
     * @return a Pane object representing the runner up view element with all the needed information
     */
    public Pane makeRunnerUpCard(Dog dog, int place) {
        Circle portrait = new Circle();
        portrait.setRadius(35);

        User user = viewModel.getUserByHandle(dog.getOwnerName());
        byte[] imageurl = user.getImageURL();
        if (imageurl != null) {
            Image img = new Image(new ByteArrayInputStream(imageurl));
            portrait.setStroke(Color.web("#5B8266"));
            portrait.setStrokeWidth(2);
            portrait.setFill(new ImagePattern(img));
        }                                                                                                // profile pic
        portrait.setPickOnBounds(true);

        portrait.setOnMouseClicked(e -> {
            viewModel.openUserProfile(dog.getOwnerName());
            viewHandler.openView("profile");
        });
        VBox left = new VBox(portrait);
        left.setAlignment(Pos.TOP_CENTER);
        left.setSpacing(0);

        User user1 = viewModel.getUserByHandle(dog.getOwnerName());
        Label username = new Label(" @" + user1.getHandle() + " ● dog owner");                 /// username
        username.setPickOnBounds(true);
        username.setOnMouseClicked(e -> {
            viewHandler.openView("profile");
            viewModel.openUserProfile(dog.getOwnerName());
        });

        HBox userTime = new HBox(username);
        userTime.setAlignment(Pos.CENTER_LEFT);

        Label text1 = new Label(dog.getName());
        text1.getStyleClass().add("name");                                     //dog name itself + place
        text1.setPrefHeight(30);
        text1.setPrefWidth(200);
        text1.wrapTextProperty().set(true);

        Label text2 = new Label(place + "th place");
        text2.getStyleClass().add("post");
        text2.setPrefHeight(20);
        text2.setPrefWidth(200);

        Rectangle photo = new Rectangle();
        if (dog.getImageURL() != null) {
            photo.setWidth(75);
            photo.setHeight(75);
            photo.setStroke(Color.web("#f09c48"));
            photo.setStrokeWidth(2);                                                            //image
            javafx.scene.image.Image img2 = new javafx.scene.image.Image(new ByteArrayInputStream(dog.getImageURL()));
            photo.setFill(new ImagePattern(img2));
        }

        HBox dogPhoto = new HBox(photo);
        dogPhoto.setAlignment(Pos.CENTER_LEFT);
        dogPhoto.setSpacing(5);


        VBox right = new VBox(userTime, text2, text1, dogPhoto);
        right.setPadding(new Insets(5, 5, 5, 5));

        HBox hBox = new HBox(left, right);                                            //bring everything together
        hBox.setSpacing(2);
        hBox.setPadding(new Insets(10, 15, 15, 10));

        Pane pane = new Pane(hBox);
        pane.setPrefWidth(250);
        if (dog.getImageURL() != null)
            pane.setPrefHeight(180);
        else
            pane.setPrefHeight(110);                                           //create the pane with everything inside

        pane.getStyleClass().add("mainFxmlClass");
        return pane;
    }

}
