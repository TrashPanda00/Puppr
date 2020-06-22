package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import model.TextLimiter;
import viewmodel.EditCommentViewModel;

/**
 * The EditCommentViewController class handles the look and response to certain events of the editCommentView
 * without implementing any logic.
 *
 * @author Natali Munk-Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class EditCommentViewController {

    private Region root;
    private EditCommentViewModel viewModel;
    private ViewHandler viewHandler;

    @FXML
    private TextArea textArea;

    /**
     * A method for initialising all the class variables and binding the view elements to the ViewModel properties
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param editCommentViewModel
     *        an instance of the EditCommentViewModel class
     * @param root
     *        an instance of the Region class referencing the current region
     */
    public void init(ViewHandler viewHandler, EditCommentViewModel editCommentViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = editCommentViewModel;
        this.root = root;
        textArea.textProperty().bindBidirectional(viewModel.commentProperty());
        TextLimiter.addTextLimiter(textArea,500);
    }

    /**
     * A method called when the 'post' button for a comment is pressed. The method
     * checks for blank fields then calls a ViewModel class and closes the view.
     */
    public void commentButtonClicked() {

        if (textArea.getText() == null) {
            textArea.setStyle("-fx-border-color: red ; ");
        } else if (textArea.getText().isBlank()) {
            textArea.setStyle("-fx-border-color: red ; ");
        } else {
            textArea.setStyle(null);
            viewModel.editComment();
            close();
        }
    }

    /**
     * Getter for the region instance
     * @return a reference to the <code>root</code> value
     */
    public Region getRoot() {
        return root;
    }

    /**
     * A method for resetting the fields when the view is opened again
     */
    public void reset() {
        textArea.clear();
    }

    /**
     * A method for closing the view
     */
    public void close() {
        viewHandler.openView("viewPost");
    }
}
