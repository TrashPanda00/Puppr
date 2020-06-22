package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import model.Comment;
import model.TextLimiter;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import viewmodel.CreateCommentViewModel;

/**
 * The CreateCommentViewController class handles the look and reaction to some events for the createCommentView
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class CreateCommentViewController implements LocalListener<Object,Object>
{
  private Region root;
  private CreateCommentViewModel viewModel;
  private ViewHandler viewHandler;

  @FXML private TextArea textArea;
  private boolean fromHome;
  private String handle;

  /**
   * A method initialising the class fields and binding fxml elements to their ViewModel properties
   * @param viewHandler
   *        an instance of the ViewHandler class
   * @param createCommentViewModel
   *        an instance of the CreateCommentViewModel class
   * @param root
   *        an instance of the Region class
   */
  public void init(ViewHandler viewHandler, CreateCommentViewModel createCommentViewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = createCommentViewModel;
    this.viewModel.addListener(this);
    this.root = root;
    textArea.textProperty().bindBidirectional(viewModel.commentProperty());
    TextLimiter.addTextLimiter(textArea, 500);
  }

  private boolean areFieldsNotBlank(){
    boolean ok = true;
    if(textArea.getText() == null) {
      textArea.setStyle("-fx-border-color: red ; ");
      ok=false;
    }else if(textArea.getText().isBlank()){
      textArea.setStyle("-fx-border-color: red ; ");
      ok=false;
    }
    return ok;
  }

  /**
   * A method called on a button press, checking for empty fields, calling
   * a ViewModel method then closing the view.
   */
  public void commentButtonClicked(){
    if(areFieldsNotBlank()){
      textArea.setStyle(null);
      viewModel.addComment();
      close();
    }
  }

  /**
   * Getter for the Region instance
   * @return a reference to the <code>root</code> field
   */
  public Region getRoot() {
    return root;
  }

  /**
   * A method called on resetting the view which clears the fields
   */
  public void reset(){
    textArea.clear();
  }

  /**
   * A method for closing the view
   */
  public void close()
  {
    if(fromHome)
      viewHandler.openView("home");
    else
      viewHandler.openView("viewPost");
  }

  /**
   * A method called whenever the subject property changes value and fires an event
   * @param event
   *        the event that was heard by the listener
   */
  @Override
  public void propertyChange(ObserverEvent<Object, Object> event) {
    if(event.getPropertyName().equals("comment")){
      fromHome=(boolean)event.getValue2();
      handle=event.getValue1().toString();
    }
  }
}