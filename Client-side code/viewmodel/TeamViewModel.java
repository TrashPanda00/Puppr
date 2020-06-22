package viewmodel;

import model.LocalModel;

/**
 * The TeamViewModel class handles the logic behind the team view
 *
 * @author Natali-Munk Jakobsen
 * @version 1.0
 */
public class TeamViewModel {

    private LocalModel model;

    /**
     * A constructor setting the local variable
     * @param model
     *          an instance of the LocalModel interface
     */
    public TeamViewModel(LocalModel model){
        this.model=model;
    }
}
