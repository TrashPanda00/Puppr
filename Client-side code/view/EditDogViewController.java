package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.Dog;
import model.ImageConverter;
import model.TextLimiter;
import viewmodel.EditDogViewModel;

import java.io.*;

/**
 * The EditDogViewController class handles the look and the response to certain events of the editDogView.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class EditDogViewController {
    private Region root;
    private EditDogViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML
    private TextField dogNameField;
    @FXML
    private TextArea dogText;
    @FXML
    private Rectangle dogPicture;

    /**
     * A method for initialising all the class variables and binding the view elements to the ViewModel properties
     * @param viewHandler
     *        an instance of the ViewHandle class
     * @param editDogViewModel
     *        an instance of the EditDogViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, EditDogViewModel editDogViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = editDogViewModel;
        this.root = root;
        dogNameField.textProperty().bindBidirectional(viewModel.dogNameProperty());
        dogText.textProperty().bindBidirectional(viewModel.dogInfoProperty());

        TextLimiter.addTextLimiter(dogNameField, 50);
        TextLimiter.addTextLimiter(dogText,100);
    }

    /**
     * A method for resetting the view's fields when the view is opened again
     */
    public void reset() {
        dogNameField.clear();
        dogText.clear();
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("profile");
    }

    /**
     * Getter for the root variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A private method checking if there are any blank important fields
     * @return a boolean value of true if there are no blank fields, respectively
     * false if there are one or more blank fields.
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
     * A method called when the 'edit' button is pressed. The method checks for blank fields
     * then class the ViewModel method and closed the view.
     */
    public void editDogButtonClicked() {
        if (areFieldsNotBlank()) {
            viewModel.editDog();
            viewHandler.openView("home");
            dogNameField.setStyle(null);
        }
    }

    final FileChooser fileChooser = new FileChooser();

    /**
     * A method for setting a image to the dog. The method lets the user select an image from their
     * own device and add it to the modified Dog object.
     */
    @FXML
    public void imageButtonClicked() {
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                viewModel.setImageurl(ImageConverter.ImageToByte(file));
                Image img = new Image(new ByteArrayInputStream(viewModel.getImageurl()));
                dogPicture.setFill(new ImagePattern(img));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}