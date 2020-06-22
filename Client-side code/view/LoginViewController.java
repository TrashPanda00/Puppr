package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.TextLimiter;
import viewmodel.LoginViewModel;

/**
 * The LoginViewController class handles the look and response to certain events of the first view,
 * the loginView.
 *
 * @author Daria-Maria Popa
 * @author Natali-Munk Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class LoginViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ImageView iconX;

    private Region root;
    private LoginViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * A method for initialising all the class variables and binding the view elements to
     * the ViewModel properties
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param loginViewModel
     *        an instance of the LoginViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = loginViewModel;
        this.root = root;
        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());

        TextLimiter.addTextLimiter(usernameField, 50);
        TextLimiter.addTextLimiter(passwordField, 64);
    }

    /**
     * Getter for the Region type variable
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method resetting the view fields whenever the view is reopened
     */
    public void reset() {
        usernameField.clear();
        passwordField.clear();
    }

    /**
     * A method for closing the current view
     */
    public void close() {
        Stage stage = (Stage) iconX.getScene().getWindow();
        stage.close();
    }

    /**
     * A method checking is all the necessary fields are not blank
     * @return a boolean value equal to true if no fields are blank or false
     * if one or more fields are blank
     */
    public boolean areFieldsNotBlank() {
        boolean ok = true;
        if (usernameField.getText() == null) {
            usernameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (usernameField.getText().isBlank()) {
            usernameField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }
        if (passwordField.getText() == null) {
            passwordField.setStyle("-fx-border-color: red ; ");
            ok = false;
        } else if (passwordField.getText() == null) {
            passwordField.setStyle("-fx-border-color: red ; ");
            ok = false;
        }

        return ok;
    }

    /**
     * A method for loggin in which checks the user credentials and, if the user exists and is not blocked by the admin,
     * logs them in and opens the home view.
     */
    public void login() {
        if(areFieldsNotBlank()){
            usernameField.setStyle(null);
            passwordField.setStyle(null);
            if (viewModel.setCredentials()&&viewModel.getUserByHandle(usernameField.getText()).getStatus().equalsIgnoreCase("unblocked")) {
                viewModel.userLogin();
                viewHandler.openView("home");
            }
            else if(viewModel.setCredentials()&&viewModel.getUserByHandle(usernameField.getText()).getStatus().equalsIgnoreCase("blocked")){
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setContentText("This user account is blocked by admin");
                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getDialogPane().getButtonTypes().add(okButton);
                alert.showAndWait();
                reset();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setContentText("Incorrect username or password");
                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getDialogPane().getButtonTypes().add(okButton);
                alert.showAndWait();
                reset();
            }
        }
    }

    /**
     * A method letting a person view the home page as a guest
     */
    public void guestEnter(){
        viewModel.guestEnter();
        viewHandler.openView("home");
    }

    /**
     * A method opening up the sign up form
     */
    public void signUp() {
        viewHandler.openView("signup");
    }

    /**
     * A method switching from username field to password field when the user presses the Enter
     * key on the keyboard
     */
    @FXML
    public void onEnterUsername() {
        passwordField.requestFocus();
    }

    /**
     * A method switching from password field to calling the login method when the user presses the Enter
     * key on the keyboard
     */
    @FXML
    public void onEnterPassword() {
        login();
    }
}