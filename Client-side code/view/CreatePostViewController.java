package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.ImageConverter;
import model.Post;
import model.TextLimiter;
import viewmodel.CreatePostViewModel;

import java.io.*;
import java.time.LocalDateTime;

/**
 * The CreatePostViewController class handles the look and response of the createPostView to certain events
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class CreatePostViewController {
    private Region root;
    private CreatePostViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML private Rectangle photo;
    @FXML private TextArea textArea;
    private byte[] imageUrl;

    /**
     * A method initialising the class variables and binding the view elements to their respective
     * ViewModel property
     * @param viewHandler
     *          an instance of the ViewHandler class
     * @param createPostViewModel
     *          an instance of the CreatePostViewModel class
     * @param root
     *          an instance of the Region class
     */
    public void init(ViewHandler viewHandler, CreatePostViewModel createPostViewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = createPostViewModel;
        this.root = root;
        textArea.textProperty().bindBidirectional(viewModel.postProperty());
        imageUrl=null;

        TextLimiter.addTextLimiter(textArea,500);
    }

    /**
     * A method called when the 'post' button is presses, checking for blank fields, calling a
     * ViewModel method and closing the view
     */
    public void postButtonClicked(){
        if(textArea.getText()==null)
            textArea.setStyle("-fx-border-color: red ; ");
        else if (textArea.getText().isBlank())
            textArea.setStyle("-fx-border-color: red ; ");
        else {
            textArea.setStyle(null);
            viewModel.setImage(imageUrl);
            viewModel.addPost();
            close();
        }
    }

    /**
     * Getter for the root variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method resetting all the fields when the view is opened again
     */
    public void reset(){
        textArea.clear();
        textArea.setStyle(null);
        imageUrl=null;
        photo.setFill(null);
    }

    /**
     * A method for closing the view and opening another one
     */
    public void close()
    {
        viewHandler.openView("home");
    }


    final FileChooser fileChooser = new FileChooser();

    /**
     * A method for adding a photo to the post. The method lets the user
     * pick an image from their own device and add it to the post object.
     */
    @FXML public void addPhoto() {
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                imageUrl= ImageConverter.ImageToByte(file);
                Image img = new Image(new ByteArrayInputStream(imageUrl));
                photo.setFill(new ImagePattern(img));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}