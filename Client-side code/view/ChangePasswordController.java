package view;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Region;
import model.TextLimiter;
import viewmodel.ChangePasswordViewModel;

/**
 * The ChangePasswordController handles how the change password view looks and reacts to certain events.
 *
 * @author Natali Munk-Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class ChangePasswordController {
    private Region root;
    private ChangePasswordViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repeatPasswordField;

    /**
     * A method initialising the important fields and binding fxml elements to the related ViewModel class
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param changePasswordViewModel
     *        an instance of the ChangePasswordViewModel class
     * @param root
     *        an instance of the Region class
     */
    public void init(ViewHandler viewHandler, ChangePasswordViewModel changePasswordViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = changePasswordViewModel;
        this.root = root;
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
        newPasswordField.textProperty().bindBidirectional(viewModel.newPasswordProperty());
        repeatPasswordField.textProperty().bindBidirectional(viewModel.repeatPasswordProperty());

        TextLimiter.addTextLimiter(passwordField,64);
        TextLimiter.addTextLimiter(repeatPasswordField,64);
    }

    /**
     * A method for resetting field values
     */
    public void reset() {
        passwordField.clear();
        passwordField.setStyle("-fx-border-style: none ; ");
        newPasswordField.clear();
        newPasswordField.setStyle("-fx-border-style: none ; ");
        repeatPasswordField.clear();
        repeatPasswordField.setStyle("-fx-border-style: none ; ");
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("manage");
    }

    /**
     * Getter for the <code>root</code> field
     * @return a reference of the root field
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method for checking if the introduced password value matches the one saved in the database
     * @return a boolean value equaling true if the value match of false otherwise
     */
    public boolean checkPassword() {
        return passwordField.getText().equals(viewModel.getUserPassword());
    }

    /**
     * A private method checking for blank fields
     * @return a boolean value signaling if any obligatory fields were left null
     */
    private boolean areFieldsNotBlank(){
        boolean ok=true;
        if (passwordField.getText() == null) {
            passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }
        else if(passwordField.getText().isBlank()) {
            passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }
        if (newPasswordField.getText()==null) {
            newPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }
        else if(newPasswordField.getText().isBlank()) {
            newPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }
        if(repeatPasswordField.getText()==null) {
            repeatPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }
        else if (repeatPasswordField.getText().isBlank()) {
            repeatPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            ok=false;
        }

        return ok;
    }

    /**
     * A method called when the 'save' button is pressed, checking the password and for any black fields
     * and sending the calling a method in the ViewModel
     */
    public void savePassword() {
        if(areFieldsNotBlank()) {
            passwordField.setStyle("-fx-border-style: none ; ");
            newPasswordField.setStyle("-fx-border-style: none ; ");
            repeatPasswordField.setStyle("-fx-border-style: none ; ");
            if (checkPassword() && (newPasswordField.getText().equals(repeatPasswordField.getText()))) {
                viewModel.savePassword();
                reset();
                viewHandler.openView("home");
            }
            if (!checkPassword()) {
                passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                passwordField.clear();
                newPasswordField.clear();
                repeatPasswordField.clear();
            }
            if (!(newPasswordField.getText().equals(repeatPasswordField.getText()))) {
                System.out.println("Passwords do not match");
                newPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                repeatPasswordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                newPasswordField.clear();
                repeatPasswordField.clear();
            }
        }
    }

}