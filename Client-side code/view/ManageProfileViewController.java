package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.ImageConverter;
import model.TextLimiter;
import model.User;
import viewmodel.ManageProfileViewModel;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The ManageProfileViewController class handles the look and response to certain events of the manageProfile view.
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class ManageProfileViewController {

    @FXML
    private Label errorLabel;
    @FXML
    private RadioButton femaleButton;
    @FXML
    private RadioButton maleButton;
    @FXML
    private TextField handleField;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField emailField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextArea bioText;
    @FXML
    private javafx.scene.shape.Rectangle profile;

    private Region root;
    private ManageProfileViewModel viewModel;
    private ViewHandler viewHandler;
    private User user;

    /**
     * A method for initialising all the class variables and binding the view elements to
     * the ViewModel properties.
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param manageProfileViewModel
     *        an instance of the ManageProfileViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, ManageProfileViewModel manageProfileViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = manageProfileViewModel;
        this.root = root;
        handleField.textProperty().bindBidirectional(viewModel.handleProperty());
        birthday.valueProperty().bindBidirectional(viewModel.birthdayProperty());
        emailField.textProperty().bindBidirectional(viewModel.emailProperty());
        lastNameField.textProperty().bindBidirectional(viewModel.lastNameProperty());
        firstNameField.textProperty().bindBidirectional(viewModel.firstNameProperty());
        bioText.textProperty().bindBidirectional(viewModel.bioProperty());
        user = viewModel.getUserInfo();
        birthday.getEditor().setDisable(true);

        TextLimiter.addTextLimiter(handleField,50);
        TextLimiter.addTextLimiter(bioText, 200);
        TextLimiter.addTextLimiter(firstNameField,50);
        TextLimiter.addTextLimiter(lastNameField,50);
        TextLimiter.addTextLimiter(emailField,50);

        birthday.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        initFields();
    }

    /**
     * A method initialising the fields to the user's current information
     */
    private void initFields() {
        errorLabel.setText("");
        if (user.getGender().equals("M")) {
            maleButton.setSelected(true);
            maleButton.requestFocus();
        } else {
            femaleButton.setSelected(true);
            femaleButton.requestFocus();
        }
        handleField.setText(user.getHandle());
        birthday.setValue(LocalDate.parse(user.getBirthday().toString()));
        emailField.setText(user.getEmail());
        lastNameField.setText(user.getLastname());
        firstNameField.setText(user.getName());
        if (user.getImageURL() != null)
            profile.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getImageURL()))));
        bioText.setText(user.getBio());
    }

    /**
     * A method resetting the fields whenever the view is reopened
     */
    public void reset() {
        clearFields();
        user = viewModel.getUserInfo();
        initFields();
    }

    /**
     * A method for closing this view and opening the home view
     */
    public void close() {
        viewHandler.openView("home");
    }

    /**
     * Getter for the Region type variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    final FileChooser fileChooser = new FileChooser();

    /**
     * A method letting the user set or modify their profile picture with an image from their own device
     */
    @FXML
    public void setImage() {
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(ImageConverter.ImageToByte(file)));
                profile.setFill(new ImagePattern(img));
                viewModel.setImageurl(ImageConverter.ImageToByte(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A method opening the changePassword view
     */
    @FXML
    public void changePassword() {
        viewHandler.openView("password");
    }

    /**
     * A method for checking is any fields are blank in the form
     * @return a boolean value equal to true is none are blank and to false if
     * one or more fields are blank
     */
    private boolean isFormComplete() {
        boolean ok = true;
        if (!maleButton.isSelected() && !femaleButton.isSelected())
            ok = false;
        if (handleField.getText() == null) {
            handleField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (handleField.getText().isBlank()) {
            handleField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }
        else
            handleField.setStyle(null);
        if (birthday.getValue() == null) {
            birthday.setStyle("-fx-border-color: red ; ");
            ok = false;
        }
        else
            birthday.setStyle(null);
        if (emailField.getText() == null) {
            emailField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (emailField.getText().isBlank()) {
            emailField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }
        else emailField.setStyle(null);
        if (lastNameField.getText() == null) {
            lastNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (lastNameField.getText().isBlank()) {
            lastNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }
        else lastNameField.setStyle(null);
        if (firstNameField.getText() == null) {
            firstNameField.setStyle("-fx-border-color: red ;");
            ok = false;
        } else if (firstNameField.getText().isBlank()) {
            firstNameField.setStyle("-fx-border-color: red ;");
            ok = false;
        }
        else firstNameField.setStyle(null);
        if (bioText.getText() == null) {
            bioText.setStyle("-fx-border-color: red ;");
            ok = false;
        } else if (bioText.getText().isBlank()) {
            bioText.setStyle("-fx-border-color: red ;");
            ok = false;
        }
        else bioText.setStyle(null);

        return ok;
    }

    /**
     * A method for clearing the fields
     */
    private void clearFields() {
        handleField.setStyle(null);
        birthday.setStyle(null);
        emailField.setStyle(null);
        lastNameField.setStyle(null);
        firstNameField.setStyle(null);
        bioText.setStyle(null);
        errorLabel.setText("");
    }

    /**
     * A method for saving the changes of the user's information and opening up the
     * home view
     */
    @FXML
    public void save() {
        if (isFormComplete()) {
            if (maleButton.isSelected())
                viewModel.setGender("M");
            else
                viewModel.setGender("F");
            viewModel.editUserInfo();
            clearFields();
            close();
        }
        else{
            errorLabel.setText("Important fields are incomplete!");
        }
    }

    /**
     * A method opening up the addDog view
     */
    @FXML
    public void addDog() {
        viewHandler.openView("addDog");
    }
}