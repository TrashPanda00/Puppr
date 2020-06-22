package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.ImageConverter;
import model.Post;
import model.TextLimiter;
import viewmodel.EditPostViewModel;

import java.io.*;

/**
 * The EditPostViewController class handles the look and response of the editPostView.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class EditPostViewController {

    private Region root;
    private EditPostViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML
    private Rectangle photo;
    @FXML
    private TextArea textArea;
    private byte[] imageUrl;
    private Post post;

    /**
     * A method for initialising all the class variables and binding the view elements
     * to the ViewModel properties.
     * @param viewHandler
     *        an instance of the ViewHandle class
     * @param editPostViewModel
     *        an instance of the EditPostViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, EditPostViewModel editPostViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = editPostViewModel;
        this.root = root;
        textArea.textProperty().bindBidirectional(viewModel.postProperty());
        imageUrl = null;
        post = viewModel.getPostById();

        TextLimiter.addTextLimiter(textArea, 500);
    }

    /**
     * A method being called when the 'post' button is pressed. The method checks for blank
     * fields then calls the ViewModel method and closes the view.
     */
    public void postButtonClicked() {
        if (textArea.getText() == null)
            textArea.setStyle("-fx-border-color: red ; ");
        else if (textArea.getText().isBlank())
            textArea.setStyle("-fx-border-color: red ; ");
        else {
            textArea.setStyle(null);
            viewModel.editPost();
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
     * A method resetting the view fields when the view is reopened
     */
    public void reset() {
        textArea.setStyle(null);
        imageUrl = null;
        photo.setFill(null);
    }

    /**
     * A method closing the view and opening another one
     */
    public void close() {
        if(viewModel.isFromHome())
            viewHandler.openView("home");
        else
            viewHandler.openView("profile");
    }

    final FileChooser fileChooser = new FileChooser();

    /**
     * A method for letting the user add a new image from their device to their edited post.
     */
    @FXML
    public void addPhoto() {

        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                imageUrl = ImageConverter.ImageToByte(file);
                javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(imageUrl));
                photo.setFill(new ImagePattern(img));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}