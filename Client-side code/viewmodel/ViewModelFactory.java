package viewmodel;

import model.LocalModel;

/**
 * The ViewModelFactory class handles creating and returning instances of the ViewModel classes
 *
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class ViewModelFactory {
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;
    private SignupViewModel signupViewModel;
    private ManageProfileViewModel manageProfileViewModel;
    private CreatePostViewModel createPostViewModel;
    private CreateCommentViewModel createCommentViewModel;
    private ChangePasswordViewModel changePasswordViewModel;
    private AddDogViewModel addDogViewModel;
    private EditDogViewModel editDogViewModel;
    private EditPostViewModel editPostViewModel;
    private ViewPostViewModel viewPostViewModel;
    private EditCommentViewModel editCommentViewModel;
    private TeamViewModel teamViewModel;
    private HallOfFameViewModel hallOfFameViewModel;

    /**
     * A constructor initialising all the ViewModel classes
     * @param model
     *          an instance of the LocalModel interface
     */
    public ViewModelFactory(LocalModel model){

        loginViewModel = new LoginViewModel(model);
        homeViewModel = new HomeViewModel(model,loginViewModel);
        profileViewModel = new ProfileViewModel(model,homeViewModel);
        signupViewModel = new SignupViewModel(model);
        manageProfileViewModel = new ManageProfileViewModel(model,homeViewModel);
        createPostViewModel = new CreatePostViewModel(model, homeViewModel);
        changePasswordViewModel=new ChangePasswordViewModel(model,loginViewModel);
        addDogViewModel=new AddDogViewModel(model,homeViewModel);
        editDogViewModel=new EditDogViewModel(model,homeViewModel,profileViewModel);
        editPostViewModel=new EditPostViewModel(model,homeViewModel,profileViewModel);
        viewPostViewModel = new ViewPostViewModel(model,homeViewModel,profileViewModel);
        createCommentViewModel=new CreateCommentViewModel(model,homeViewModel,viewPostViewModel);
        editCommentViewModel=new EditCommentViewModel(model,viewPostViewModel);
        teamViewModel=new TeamViewModel(model);
        hallOfFameViewModel=new HallOfFameViewModel(model,homeViewModel);
    }

    /**
     * Getter for the HomeViewModel
     * @return an instance of the HomeViewModel class
     */
    public HomeViewModel getHomeViewModel(){
        return homeViewModel;
    }

    /**
     * Getter for the LoginViewModel
     * @return an instance of the LoginViewModel class
     */
    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    /**
     * Getter for the ProfileViewModel
     * @return an instance of the ProfileViewModel class
     */
    public ProfileViewModel getProfileViewModel() {
        return profileViewModel;
    }

    /**
     * Getter for the SignupViewModel
     * @return an instance of the SignupViewModel class
     */
    public SignupViewModel getSignupViewModel() {
        return signupViewModel;
    }

    /**
     * Getter for the ManageProfileViewModel
     * @return an instance of the ManageProfileViewModel class
     */
    public ManageProfileViewModel getManageProfileViewModel() {
        return manageProfileViewModel;
    }

    /**
     * Getter for the CreatePostViewModel
     * @return an instance of the CreatePostViewModel class
     */
    public CreatePostViewModel getCreatePostViewModel() {
        return createPostViewModel;
    }

    /**
     * Getter for the CreateCommentViewModel
     * @return an instance of the CreateCommentViewModel class
     */
    public CreateCommentViewModel getCreateCommentViewModel()
    {
        return createCommentViewModel;
    }

    /**
     * Getter for the ChangePasswordViewModel
     * @return an instance of the ChangePasswordViewModel class
     */
    public ChangePasswordViewModel getChangePasswordViewModel()
    {
        return changePasswordViewModel;
    }

    /**
     * Getter for the AddDogViewModel
     * @return an instance of the AddDogViewModel class
     */
    public AddDogViewModel getAddDogViewModel()
    {
        return addDogViewModel;
    }

    /**
     * Getter for the EditDogViewModel
     * @return an instance of the EditDogViewModel class
     */
    public EditDogViewModel getEditDogViewModel()
    {
        return editDogViewModel;
    }

    /**
     * Getter for the EditPostViewModel
     * @return an instance of the EditPostViewModel class
     */
    public EditPostViewModel getEditPostViewModel()
    {
        return editPostViewModel;
    }

    /**
     * Getter for the ViewPostViewModel
     * @return an instance of the ViewPostViewModel class
     */
    public ViewPostViewModel getViewPostViewModel() {
        return viewPostViewModel;
    }

    /**
     * Getter for the editCommentViewModel
     * @return an instance of the EditCommentViewModel class
     */
    public EditCommentViewModel getEditCommentViewModel() {
        return editCommentViewModel;
    }

    /**
     * Getter for the TeamViewModel
     * @return an instance of the TeamViewModel class
     */
    public TeamViewModel getTeamViewModel() {
        return teamViewModel;
    }

    /**
     * Getter for the HallOfFameViewModel
     * @return an instance of the HallOfFameViewModel class
     */
    public HallOfFameViewModel getHallOfFameViewModel() {
        return hallOfFameViewModel;
    }
}
