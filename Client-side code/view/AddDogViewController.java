package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.ImageConverter;
import model.TextLimiter;
import viewmodel.AddDogViewModel;

import java.io.*;

/**
 * The AddDogViewController class binds the View to the ViewModel and dictates how the addDogView looks and reacts
 * to certain mouse or keyboard events.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class AddDogViewController {
    private Region root;
    private AddDogViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML
    private TextField dogNameField;
    @FXML
    private TextArea dogText;
    @FXML
    private Rectangle dogPicture;

    /**
     * A method for initialising the class variables and binding the fields to the ViewModel properties
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param addDogViewModel
     *        an instance of the AddDogViewModel class
     * @param root
     *        an instance of the Region class
     */
    public void init(ViewHandler viewHandler, AddDogViewModel addDogViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = addDogViewModel;
        this.root = root;
        dogNameField.textProperty().bindBidirectional(viewModel.dogNameProperty());
        dogText.textProperty().bindBidirectional(viewModel.dogInfoProperty());

        TextLimiter.addTextLimiter(dogNameField, 12);
        TextLimiter.addTextLimiter(dogText, 100);
    }

    /**
     * A method for the resenting the fields' values
     */
    public void reset() {
        dogNameField.clear();
        dogText.clear();
        dogPicture.setFill(null);
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("manage");
    }

    /**
     * Getter for the Region instance
     * @return a reference to the <code>root</code> variable
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A private method checking for blank fields
     * @return a boolean value signaling if any obligatory fields were left null
     */
    private boolean areFieldsNotBlank() {
        boolean ok=true;
        if (dogNameField.getText() == null) {
            dogNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (dogNameField.getText().isBlank()) {
            dogNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }

        return ok;
    }

    /**
     * A method called on a button clicked event, checking for blank fields and
     * calling the ViewModel.
     */
    public void addDogButtonClicked() {
        if (areFieldsNotBlank()) {
            viewModel.addDog();
            viewHandler.openView("manage");
            dogNameField.setStyle(null);
        }
    }

    final FileChooser fileChooser = new FileChooser();

    /**
     * A method called on 'image' button clicked. This method lets the user pick an image file and upload it,
     * setting it as the dog's profile image.
     */
    @FXML
    public void imageButtonClicked() {
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                viewModel.setImageurl(ImageConverter.ImageToByte(file));
                javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(viewModel.getImageurl()));
                dogPicture.setFill(new ImagePattern(img));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}