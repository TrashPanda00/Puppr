package view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import model.Comment;
import model.Post;
import model.User;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import viewmodel.ViewPostViewModel;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The ViewPostViewController handles the look and response to certain events of the viewPostView.
 * This view contains and displays the information about a certain post and its comments
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 */

public class ViewPostViewController implements LocalListener<Object, Object> {
    @FXML
    private VBox vBox;
    @FXML
    private VBox postVBox;
    private Region root;
    private ViewPostViewModel viewModel;
    private ViewHandler viewHandler;

    private boolean home;
    private ArrayList<String> likedPosts;
    private Post currentPost;
    private String currentUserHandle;
    private ArrayList<String> likedComments;
    private boolean isAdmin;
    private boolean isGuest;

    /**
     * A method for initialising all the class variables
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param viewModel
     *        an instance of the ViewPostViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, ViewPostViewModel viewModel, Region root) {
        this.root = root;
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.viewModel.addListener(this);

        likedPosts = viewModel.getAllLikes();
        likedComments=viewModel.getAllCommentLikes();
        vBox.getChildren().clear();
        postVBox.getChildren().clear();
        if (currentPost == null)
            currentPost = viewModel.getCurrentPost();
        currentUserHandle=viewModel.getCurrentUserHandle();

        isGuest=viewModel.isGuest();

        if(!isGuest){
            if(viewModel.getUserByHandle(currentUserHandle).getUserType().equals("admin"))
            {
                isAdmin=true;
            }
            else {
                isAdmin=false;
            }
        }

        addPost();
        addComments();
    }

    /**
     * Getter for the Region type variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method for resetting the fields when the view is reopened
     */
    public void reset() {
        likedPosts = viewModel.getAllLikes();
        likedComments = viewModel.getAllCommentLikes();
        vBox.getChildren().clear();
        if (currentPost != null)
            addPost();
        addComments();
    }

    /**
     * A method for adding the post's information to the view
     */
    private void addPost() {
        postVBox.getChildren().clear();
        postVBox.getChildren().add(makeCard(viewModel.getCurrentPost()));
    }

    /**
     * A method creating the card with the post's information
     * @param post
     *          the post object whose information will be displayed in the card
     * @return a Pane object representing the post card with all the needed information displayed in it
     */
    public Pane makeCard(Post post) {
        Circle portrait = new Circle();
        portrait.setRadius(37);

        User user = viewModel.getUserByHandle(post.getHandle());
        byte[] imageurl = user.getImageURL();
        Image img = new Image(new ByteArrayInputStream(imageurl));

        portrait.setStroke(Color.web("#5B8266"));
        portrait.setStrokeWidth(3);
        portrait.setFill(new ImagePattern(img));
        portrait.setOnMouseClicked(e -> {                                   //profile pic
            viewModel.openUserProfile(post.getHandle());
            viewHandler.openView("profile");
        });


        String verify2 = "";
        try
        {
            verify2 = post.getPostId() + "/" + currentUserHandle;
        }
        catch(Exception e)
        {

        }


        boolean found2 = false;
        if(likedPosts!=null) {
            for (String like : likedPosts) {
                if (like.equals(verify2)) {
                    found2 = true;
                    break;
                }
            }
        }

        ImageView bone;
        if(!found2)
            bone = new ImageView("resources/boneLike.png");
        else
            bone = new ImageView("resources/boneFilled.png");


        bone.setFitHeight(45);                                              //like button
        bone.setFitWidth(45);
        bone.setEffect(new ColorAdjust(0, 0, -0.14, -1));
        bone.setPickOnBounds(true);

        if(isGuest){
            bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> guestAlert("You have to login to use like button "));
        }
        else {
            bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String verify = post.getPostId() + "/" + currentUserHandle;

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
                    viewModel.likePost(post.getPostId(), currentUserHandle);
                    vBox.getChildren().clear();
                    addPost();
                    addComments();
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
        if(isGuest){
            comment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> guestAlert("You have to login to use comment button "));

        }
        else {
            comment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                viewModel.setComment(post);
                viewHandler.openView("createComment");
            });
        }
        VBox left = new VBox(portrait, bone, comment);
        left.setAlignment(Pos.TOP_CENTER);
        left.setSpacing(0);


        User user1 = viewModel.getUserByHandle(post.getHandle());
        Label username = new Label(user1.getName()+" "+user1.getLastname() + " ●");                //username
        username.getStyleClass().add("name");
        username.setOnMouseClicked(e -> {
            viewHandler.openView("profile");
            viewModel.openUserProfile(post.getHandle());

        });

        Label time = new Label(" " + post.getTimePosted().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + post.getTimePosted().format(
            DateTimeFormatter.ofPattern("HH:mm:ss")));                       //time

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
        right = new VBox(userTime, handle1, text1, photo);
        left.setSpacing(27);


        HBox hBox = new HBox(left, right);                                            //bring everything together
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(10, 17, 10, 20));

        Pane pane = new Pane(hBox);
        pane.setPrefWidth(574);
        if (post.getImageURL() != null)
            pane.setPrefHeight(580);
        else
            pane.setPrefHeight(274);                                           //create the pane with everything inside

        pane.getStyleClass().add("mainFxmlClass");
        return pane;
    }

    /**
     * A method making the comment cards with all the needed information for the comments
     * @param comment
     *          the Comment object whose information will be displayed in the card
     * @return a Pane object representing the comment card with all the information contained in it
     */
    public Pane makeCommentCard(Comment comment) {
        Circle portrait = new Circle();
        portrait.setRadius(37);

        User user = viewModel.getUserByHandle(comment.getHandle());
        byte[] imageurl = user.getImageURL();
        Image img = new Image(new ByteArrayInputStream(imageurl));

        portrait.setStroke(Color.web("#5B8266"));
        portrait.setStrokeWidth(3);
        portrait.setFill(new ImagePattern(img));
        portrait.setOnMouseClicked(e -> {
            viewModel.openUserProfile(comment.getHandle());
            viewHandler.openView("profile");
        });






        String verify2 = "";
        try
        {
            verify2 = comment.getCommentId() + "/" + currentUserHandle;
        }
        catch(Exception e)
        {

        }

        ImageView bone;
        boolean found2 = false;
        if(likedComments != null)
        {
            for(String like : likedComments)
            {
                if(like.equals(verify2))
                {
                    found2 = true;
                    break;
                }
            }
        }

        if(!found2)
            bone = new ImageView("resources/boneLike.png");
        else
            bone = new ImageView("resources/boneFilled.png");


        bone.setFitHeight(45);
        bone.setFitWidth(45);
        bone.setEffect(new ColorAdjust(0, 0, -0.14, -1));
        bone.setPickOnBounds(true);

        if(isGuest){
            bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> guestAlert("You have to login to use like button "));
        }
        else {
            bone.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String verify = comment.getCommentId() + "/" + currentUserHandle;
                boolean found = false;
                if(likedComments!=null) {
                    for (String like : likedComments) {
                        if (like.equals(verify)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    viewModel.likeComment(comment.getCommentId(), currentUserHandle);
                    likedComments = viewModel.getAllCommentLikes();
                    bone.setImage(new Image("resources/boneFilled.png"));
                    reset();
                }
            });

        }

        VBox left = new VBox(portrait, bone);
        left.setAlignment(Pos.TOP_CENTER);
        left.setSpacing(0);


        Label username = new Label(comment.getHandle() + " ●");                //username
        username.getStyleClass().add("name");
        username.setOnMouseClicked(e -> {
            viewHandler.openView("profile");
            viewModel.openUserProfile(comment.getHandle());

        });

        Label time = new Label(" " + comment.getTimePosted().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + comment.getTimePosted().format(
            DateTimeFormatter.ofPattern("HH:mm:ss")));                       //time

        HBox userTime = new HBox(username, time);
        userTime.setAlignment(Pos.CENTER_LEFT);


        String aux = comment.getLikes() + " likes " ;
        Label handle1 = new Label(aux);
        handle1.getStyleClass().add("userName");


        Label text1 = new Label(comment.getBody());
        text1.getStyleClass().add("post");                                     //post itself
        text1.setPrefHeight(120);
        text1.setPrefWidth(434);
        text1.wrapTextProperty().set(true);
        VBox right;
        if((currentUserHandle.equalsIgnoreCase(comment.getHandle()))||isAdmin){
            Label edit = new Label("Edit");
            edit.getStyleClass().add("button");
            edit.setPrefWidth(60);
            edit.setOnMouseClicked(e -> {
                viewModel.editComment(comment);
                viewHandler.openView("editComment");
            });
            Label delete = new Label("Delete");
            delete.setPrefWidth(60);
            delete.getStyleClass().add("button");
            delete.setOnMouseClicked(e -> {
                viewModel.deleteComment(comment);
                reset();
            });

            HBox end = new HBox(edit, delete);
            end.setSpacing(20);
            end.setPadding(new Insets(40, 0, 0, 50));
            end.setAlignment(Pos.BOTTOM_RIGHT);

            right = new VBox(userTime, handle1, text1,  end);
        } else {

            right = new VBox(userTime, handle1, text1);
        }
        left.setSpacing(27);

        HBox hBox = new HBox(left, right);                                            //bring everything together
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(10, 17, 10, 20));

        Pane pane = new Pane(hBox);
        pane.setPrefWidth(574);

        pane.setPrefHeight(274);                                           //create the pane with everything inside

        pane.getStyleClass().add("mainFxmlClass");
        return pane;
    }

    /**
     * A method for adding the comment cards to the view
     */
    private void addComments(){
        ArrayList<Comment> comments=viewModel.getCommentForPost(currentPost.getPostId());
        for(Comment comment:comments){
            vBox.getChildren().add(makeCommentCard(comment));
        }

    }

    /**
     * A method for closing the view
     */
    public void close() {
        home = viewModel.isFromHome();
        if (home)
            viewHandler.openView("home");
        else
            viewHandler.openView("profile");
    }

    /**
     * A method being called when a subject property changes and fires an event
     * @param event
     *          the event fired by the subject and listened for here
     */
    @Override
    public void propertyChange(ObserverEvent<Object, Object> event) {
        if (event.getPropertyName().equals("viewPost")) {
            currentPost = viewModel.getPostById((int) event.getValue2());
            makeCard(currentPost);
            addPost();
            currentUserHandle = event.getValue1().toString();
        } else if (event.getPropertyName().equals("comment")) {
            vBox.getChildren().clear();
            addPost();
            addComments();
        }
        if(event.getPropertyName().equals("guest")){
            isGuest= (boolean) event.getValue2();
            vBox.getChildren().clear();
            addPost();
            addComments();
        }

        if(event.getPropertyName().equals("user")){
            isGuest= (boolean) event.getValue2();
        }

    }

    /**
     * A method displaying an alert if a guest type user tries to do anything restricted for only logged in users, such
     * as liking or commenting
     * @param message
     *          the message being shown to the guest
     */
    public void guestAlert(String message){
        ButtonType guest = new ButtonType("Continue as a guest", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType login = new ButtonType("Go to login page", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, message, login, guest);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(login) == guest) {
            //do nothing
        }
        else {
            viewHandler.openView("login");
        }
    }
}


