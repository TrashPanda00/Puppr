package view;

import javafx.scene.layout.Region;
import viewmodel.TeamViewModel;

/**
 * The TeamViewController class handles the team view and its components.
 *
 * @author Natali Munk-Jakobsen
 * @author Daria-Maria Popa
 * @author Bogdan-Alexandru Mezei
 */
@SuppressWarnings("EmptyMethod")
public class TeamViewController {

    private Region root;
    private TeamViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * A method for initialising all the class variables
     * @param viewHandler
     *        an instance of the ViewHandler class
     * @param teamViewModel
     *        an instance of the TeamViewModel class
     * @param root
     *        an instance of the current Region
     */
    public void init(ViewHandler viewHandler, TeamViewModel teamViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = teamViewModel;
        this.root = root;

    }

    /**
     * A method for resetting the class fields when the view is reopened
     */
    public void reset() {}

    /**
     * A method for closing the view
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
}
