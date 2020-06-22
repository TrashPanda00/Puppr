package view;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Post;

import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import viewmodel.HomeViewModel;

import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The HomeViewController class handles the look and response to different events of the homeView.
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class HomeViewController implements LocalListener<Object, Object> {
    @FXML
    private ImageView create;
    @FXML
    private Circle portrait;
    @FXML
    private VBox vBox;
    @FXML
    Label handleLabel;
    @FXML
    Label nameLabel;
    @FXML
    Button newPost;
    @FXML
    private HBox myProfileHBox;
    @FXML
    private HBox manageProfileHBox;
    @FXML
    private HBox theTeamHBox;
    private ArrayList<String> likedPosts;
    private boolean isAdmin;
    private boolean isGuest;


    private Region root;
    private HomeViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * A method for initialising the class variables and binding the view elements to the ViewModel
     * properties. The init method also adds the listeners for the events in ViewModel class.
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param homeViewModel
     *        an instance of the HomeViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, HomeViewModel homeViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = homeViewModel;
        viewModel.addListener(this, "user");
        viewModel.addListener(this, "guest");
        viewModel.addListener(this, "post");
        viewModel.addListener(this, "admin");
        this.root = root;
        likedPosts = viewModel.getAllLikes();
        handleLabel.textProperty().bindBidirectional(viewModel.handleProperty());
        nameLabel.textProperty().bindBidirectional(viewModel.nameProperty());
        vBox.getChildren().clear();
        addLastPosts();
    }

    /**
     * Getter for the Region type variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method resetting the fields when the view is reopened
     */
    public void reset() {
        likedPosts = viewModel.getAllLikes();
        vBox.getChildren().clear();
        addLastPosts();
    }

    /**
     * A method modifying the look of buttons when pressed
     */
    public void pressed() {
        create.setScaleY(0.9);
        create.setScaleX(0.9);
    }

    /**
     * A method modifying the look of buttons when released
     */
    public void released() {
        create.setScaleY(1);
        create.setScaleX(1);
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("login");
    }

    /**
     * A method opening the createPost view
     */
    public void makeNewPost() {
        viewHandler.openView("createPost");
    }

    /**
     * A method opening the manageProfile view
     */
    public void manageProfile() {
        viewHandler.openView("manage");
    }

    /**
     * A method opening the team view
     */
    public void theTeam() {
        viewHandler.openView("team");
    }

    /**
     * A method for opening the profile view
     */
    public void myProfile() {
        viewModel.openProfile();
        viewHandler.openView("profile");
    }

    /**
     * A method for opening the hall of fame view
     */
    public void hallOfFame() {
        viewHandler.openView("hallOfFame");
    }

    /**
     * A method for adding all the posts to the home view for users to see and interact with
     */
    public void addLastPosts() {
        ArrayList<Post> posts = viewModel.getPostList();
        int size = posts.size() - 1;

        for (int i = size; i >= 0; i--) {
            vBox.getChildren().add(makeCard(posts.get(i)));
        }
    }

    /**
     * A method making the post cards with all the owner and post information, as well as some event buttons
     * @param post
     *        the Post object whose information will be in the card
     * @return a Pane object representing the post card with all the needed information
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

        portrait.setOnMouseClicked(e -> {                                   //profile pic
            viewModel.openUserProfile(post.getHandle());
            viewHandler.openView("profile");
        });

        String verify2 = "";
        try
        {
            verify2 = post.getPostId() + "/" + handleLabel.getText().replace(" @", "");
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
                String verify = post.getPostId() + "/" + handleLabel.getText().replace(" @", "");
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
                    viewModel.likePost(post.getPostId(), handleLabel.getText().replace(" @", ""));
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
        username.setOnMouseClicked(e -> {
            viewHandler.openView("profile");
            viewModel.openUserProfile(post.getHandle());
        });
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
     * A method making the menu buttons visible again after being hidden for a guest type user
     */
    private void makeButtonsVisible(){
        myProfileHBox.setVisible(true);
        manageProfileHBox.setVisible(true);
        theTeamHBox.setVisible(true);
        newPost.setVisible(true);
        create.setVisible(true);
    }
    /**
     * A method being called whenever a subject property changes in the HomeViewModel class.
     * The method acts in different ways depending on the property name.
     * @param event
     *          the event that was fired and heard by the listeners
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        Platform.runLater(() -> {

            switch (event.getPropertyName()) {
                case "user":
                    isGuest=false;
                    makeButtonsVisible();
                    User user = viewModel.getUserByHandle(event.getValue2().toString());
                    byte[] imageurl = user.getImageURL();
                    if (imageurl != null) {
                        Image img = new Image(new ByteArrayInputStream(imageurl));
                        portrait.setFill(new ImagePattern(img));
                    }
                    break;
                case "post":
                    vBox.getChildren().clear();
                    addLastPosts();
                    break;
                case "admin":
                    isGuest=false;
                    makeButtonsVisible();
                    isAdmin = (boolean) event.getValue2();
                    vBox.getChildren().clear();
                    addLastPosts();
                    break;
                case "guest":
                    isGuest = (boolean) event.getValue2();
                    Image img = new Image("resources/default-user.png");
                    portrait.setFill(new ImagePattern(img));

                    myProfileHBox.setVisible(false);
                    manageProfileHBox.setVisible(false);
                    theTeamHBox.setVisible(false);
                    newPost.setVisible(false);
                    create.setVisible(false);

                    vBox.getChildren().clear();
                    addLastPosts();
                    break;
            }
        });
    }
}