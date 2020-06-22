package view;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javafx.util.StringConverter;
import model.ImageConverter;
import model.TextLimiter;
import model.User;
import viewmodel.SignupViewModel;


import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The SignupViewController handles the look and response to certain events of the signupView.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class SignupViewController {

    @FXML
    public Rectangle profile;
    @FXML
    public TextField passwordField;
    @FXML
    public TextArea bioText;
    @FXML
    public Label errorLabel;
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
    private Region root;
    private SignupViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * A method for initialising all the class variables and binding the view elements to
     * the ViewModel properties. It also sets the date picker to the local dd-MM-yyyy format.
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param signupViewModel
     *        an instance of the SignupViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, SignupViewModel signupViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = signupViewModel;
        this.root = root;
        handleField.textProperty().bindBidirectional(viewModel.handleProperty());
        birthday.valueProperty().bindBidirectional(viewModel.birthdayProperty());
        emailField.textProperty().bindBidirectional(viewModel.emailProperty());
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
        lastNameField.textProperty().bindBidirectional(viewModel.lastNameProperty());
        firstNameField.textProperty().bindBidirectional(viewModel.firstNameProperty());
        birthday.getEditor().setDisable(true);
        bioText.textProperty().bindBidirectional(viewModel.bioProperty());

        TextLimiter.addTextLimiter(handleField,50);
        TextLimiter.addTextLimiter(bioText, 200);
        TextLimiter.addTextLimiter(firstNameField,50);
        TextLimiter.addTextLimiter(lastNameField,50);
        TextLimiter.addTextLimiter(passwordField, 64);
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
    }

    /**
     * Getter for the Region type variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method resetting the fields whenever the view is reopened
     */
    public void reset() {
        errorLabel.setText("");
        femaleButton.selectedProperty().set(false);
        maleButton.selectedProperty().set(false);
        handleField.clear();
        birthday.setValue(null);
        emailField.clear();
        passwordField.clear();
        lastNameField.clear();
        firstNameField.clear();
        bioText.clear();

        handleField.setStyle("-fx-border-style: none ; ");
        birthday.setStyle("-fx-border-style: none ; ");
        emailField.setStyle("-fx-border-style: none ; ");
        passwordField.setStyle("-fx-border-style: none ; ");
        lastNameField.setStyle("-fx-border-style: none ; ");
        firstNameField.setStyle("-fx-border-style: none ;");
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("login");
    }

    /**
     * A private method checking if any necessary fields are blank
     * @return a boolean value equal to true is no fields are blank and false if one or more fields are blank
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
        } else
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
        } else
            emailField.setStyle(null);
        if (lastNameField.getText() == null) {
            lastNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (lastNameField.getText().isBlank()) {
            lastNameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else
            lastNameField.setStyle(null);
        if (firstNameField.getText() == null) {
            firstNameField.setStyle("-fx-border-color: red ;");
            ok = false;
        } else if (firstNameField.getText().isBlank()) {
            firstNameField.setStyle("-fx-border-color: red ;");
            ok = false;
        } else
            firstNameField.setStyle(null);
        if (passwordField.getText() == null) {
            passwordField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (passwordField.getText().isBlank()) {
            passwordField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else
            passwordField.setStyle(null);

        return ok;
    }


    private boolean isHandleTaken(){
        boolean ok = false;
        ArrayList<User> users = viewModel.getAllUsers();
        for (User user:users) {
            if(user.getHandle().equals(handleField.getText()))
                ok=true;
        }
        return ok;
    }
    /**
     * A method for signing up the user and sending the new information to the viewmodel class to be saved
     */
    @FXML
    public void SignUp() {
        if (isFormComplete()) {
            if(isHandleTaken()){
                handleField.setStyle("-fx-border-color: red ; ");
                errorLabel.setText("Handle already taken!");
            }
            else {
                if (maleButton.isSelected())
                    viewModel.setGender("M");
                else
                    viewModel.setGender("F");
                viewModel.signUpUser();
                viewHandler.openView("login");
                reset();
            }
        }else {
            errorLabel.setText("Important fields incomplete!");
        }
    }

    final FileChooser fileChooser = new FileChooser();

    /**
     * A method letting the user set a profile picture by choosing an image from their own device
     */
    @FXML
    public void setImage() {
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        if (file != null) {
            try {
                viewModel.setImageurl(ImageConverter.ImageToByte(file));
                javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(viewModel.getImageurl()));
                profile.setFill(new ImagePattern(img));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}