package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewmodel.ViewModelFactory;

/**
 * The ViewHandler class is the one in charge of switching safely from one view to the other, initialising
 * or resetting fields where needed.
 *
 * @author Daria-Maria Popa
 * @author Natali Munk-Jakobsen
 * @author Bogdan-Alexandru Mezei
 * @version 1.0
 */
public class ViewHandler {
    private Stage primaryStage;
    private Scene currentScene;
    private ViewModelFactory viewModelFactory;
    private HomeViewController homeViewController;
    private LoginViewController loginViewController;
    private ProfileViewController profileViewController;
    private SignupViewController signupViewController;
    private ChangePasswordController changePasswordViewController;
    private ManageProfileViewController manageProfileViewController;
    private CreatePostViewController createPostViewController;
    private CreateCommentViewController createCommentViewController;
    private AddDogViewController addDogViewController;
    private EditDogViewController editDogViewController;
    private EditPostViewController editPostViewController;
    private ViewPostViewController viewPostViewController;
    private EditCommentViewController editCommentViewController;
    private TeamViewController teamViewController;
    private HallOfFameViewController hallOfFameViewController;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * A constructor setting the current scene and the ViewModelFactory variable
     * @param viewModelFactory
     *          an instance of the ViewModelFactory class
     */
    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        this.currentScene = new Scene(new Region());
    }

    /**
     * A method for starting the application
     * @param primaryStage
     *          a reference to the first stage to be opened when starting the application
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("login");
    }

    /**
     * Getter for the primary stage
     * @return a reference to the <code>primaryStage</code> value
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * A method for switching between views based on their ids
     * @param id
     *          the id of the view to be opened
     */
    public void openView(String id) {
        Region root = null;
        Stage stage1 = new Stage();
        switch (id) {
            case "login":
                root = loadLoginView("loginView.fxml");
                break;
            case "home":
                root = loadHomeView("homeView.fxml");
                break;
            case "profile":
                root = loadProfileView("profileView.fxml");
                break;
            case "signup":
                root = loadSignupView("signupView.fxml");
                break;
            case "password":
                root = loadPasswordView("changePasswordView.fxml");
                break;
            case "manage":
                root = loadManageView("manageProfileView.fxml");
                break;
            case "createPost":
                root = loadCreatePostView("createPostView.fxml");
                break;
            case "createComment":
                root = loadCreateCommentView("createCommentView.fxml");
                break;
            case "addDog":
                root = loadAddDogView("addDogView.fxml");
                break;
            case "editDog":
                root = loadEditDogView("editDogView.fxml");
                break;
            case "editPost":
                root = loadEditPostView("editPostView.fxml");
                break;
            case "viewPost":
                root = loadViewPostView("viewPostView.fxml");
                break;
            case "editComment":
                root = loadEditCommentView("editCommentView.fxml");
                break;
            case "team":
                root = loadTeamView("teamView.fxml");
                break;
            case "hallOfFame":
                root = loadHallOfFameView("hallOfFameView.fxml");
                break;
        }
        currentScene.setRoot(root);
        String title = "";
        if (root != null && root.getUserData() != null) {
            title += root.getUserData();
        }
        stage1.setTitle(title);
        currentScene.setFill(Color.TRANSPARENT);
        currentScene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        currentScene.setOnMouseDragged(event -> {
            stage1.setX(event.getScreenX() - xOffset);
            stage1.setY(event.getScreenY() - yOffset);
        });
        stage1.setScene(currentScene);
        stage1.initStyle(StageStyle.TRANSPARENT);
        assert root != null;
        stage1.setWidth(root.getPrefWidth());
        stage1.setHeight(root.getPrefHeight());
        stage1.show();
        primaryStage.hide();
        primaryStage = stage1;
    }

    /**
     * A method for loading the logging in view
     * @param fxmlFile
     *          the fxml file for the loginView
     * @return a reference to the login's value for the region element
     */
    private Region loadLoginView(String fxmlFile) {
        if (loginViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                loginViewController = loader.getController();
                loginViewController.init(this, viewModelFactory.getLoginViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loginViewController.reset();
        }
        return loginViewController.getRoot();
    }

    /**
     * A method for loading the home view
     * @param fxmlFile
     *          the fxml file for the homeView
     * @return a reference to the home's value for the region element
     */
    private Region loadHomeView(String fxmlFile) {
        if (homeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                homeViewController = loader.getController();
                homeViewController.init(this, viewModelFactory.getHomeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            homeViewController.reset();
        }
        return homeViewController.getRoot();
    }

    /**
     * A method for loading the profile view
     * @param fxmlFile
     *          the fxml file for the profileView
     * @return a reference to the profile's value for the region element
     */
    private Region loadProfileView(String fxmlFile) {
        if (profileViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                profileViewController = loader.getController();
                profileViewController.init(this, viewModelFactory.getProfileViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            profileViewController.reset();
        }
        return profileViewController.getRoot();
    }

    /**
     * A method for loading the sign up view
     * @param fxmlFile
     *          the fxml file for the signupView
     * @return a reference to the signup's value for the region element
     */
    private Region loadSignupView(String fxmlFile) {
        if (signupViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                signupViewController = loader.getController();
                signupViewController.init(this, viewModelFactory.getSignupViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            signupViewController.reset();
        }
        return signupViewController.getRoot();
    }

    /**
     * A method for loading the password view
     * @param fxmlFile
     *          the fxml file for the passwordView
     * @return a reference to the password view's value for the region element
     */
    private Region loadPasswordView(String fxmlFile) {
        if (changePasswordViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                changePasswordViewController = loader.getController();
                changePasswordViewController
                        .init(this, viewModelFactory.getChangePasswordViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            changePasswordViewController.reset();
        }
        return changePasswordViewController.getRoot();
    }

    /**
     * A method for loading the manage view
     * @param fxmlFile
     *          the fxml file for the manageView
     * @return a reference to the manage view's value for the region element
     */
    private Region loadManageView(String fxmlFile) {
        if (manageProfileViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                manageProfileViewController = loader.getController();
                manageProfileViewController.init(this, viewModelFactory.getManageProfileViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageProfileViewController.reset();
        }
        return manageProfileViewController.getRoot();
    }

    /**
     * A method for loading the create post view
     * @param fxmlFile
     *          the fxml file for the createPostView
     * @return a reference to the create post view's value for the region element
     */
    private Region loadCreatePostView(String fxmlFile) {
        if (createPostViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createPostViewController = loader.getController();
                createPostViewController.init(this, viewModelFactory.getCreatePostViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            createPostViewController.reset();
        }
        return createPostViewController.getRoot();
    }

    /**
     * A method for loading the create comment view
     * @param fxmlFile
     *          the fxml file for the createCommentView
     * @return a reference to the create comment view's value for the region element
     */
    private Region loadCreateCommentView(String fxmlFile) {
        if (createCommentViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createCommentViewController = loader.getController();
                createCommentViewController.init(this, viewModelFactory.getCreateCommentViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            createCommentViewController.reset();
        }
        return createCommentViewController.getRoot();
    }

    /**
     * A method for loading the add dog view
     * @param fxmlFile
     *          the fxml file for the addDogView
     * @return a reference to the add dog view's value for the region element
     */
    private Region loadAddDogView(String fxmlFile) {
        if (addDogViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                addDogViewController = loader.getController();
                addDogViewController.init(this, viewModelFactory.getAddDogViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            addDogViewController.reset();
        }
        return addDogViewController.getRoot();
    }

    /**
     * A method for loading the edit dog view
     * @param fxmlFile
     *          the fxml file for the editDogView
     * @return a reference to the edit dog view's value for the region element
     */
    private Region loadEditDogView(String fxmlFile) {
        if (editDogViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                editDogViewController = loader.getController();
                editDogViewController.init(this, viewModelFactory.getEditDogViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editDogViewController.reset();
        }
        return editDogViewController.getRoot();
    }

    /**
     * A method for loading the edit post view
     * @param fxmlFile
     *          the fxml file for the editPostView
     * @return a reference to the edit post view's value for the region element
     */
    private Region loadEditPostView(String fxmlFile) {
        if (editPostViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                editPostViewController = loader.getController();
                editPostViewController.init(this, viewModelFactory.getEditPostViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editPostViewController.reset();
        }
        return editPostViewController.getRoot();
    }

    /**
     * A method for loading the view post view
     * @param fxmlFile
     *          the fxml file for the viewPostView
     * @return a reference to the view post's value for the region element
     */
    private Region loadViewPostView(String fxmlFile) {
        if (viewPostViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                viewPostViewController = loader.getController();
                viewPostViewController.init(this, viewModelFactory.getViewPostViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            viewPostViewController.reset();
        }
        return viewPostViewController.getRoot();
    }

    /**
     * A method for loading the edit comment view
     * @param fxmlFile
     *          the fxml file for the editCommentView
     * @return a reference to the edit comment view's value for the region element
     */
    private Region loadEditCommentView(String fxmlFile) {
        if (editCommentViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                editCommentViewController = loader.getController();
                editCommentViewController.init(this, viewModelFactory.getEditCommentViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editCommentViewController.reset();
        }
        return editCommentViewController.getRoot();
    }

    /**
     * A method for loading the team view
     * @param fxmlFile
     *          the fxml file for the teamView
     * @return a reference to the team view's value for the region element
     */
    private Region loadTeamView(String fxmlFile) {
        if (teamViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                teamViewController = loader.getController();
                teamViewController.init(this, viewModelFactory.getTeamViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            teamViewController.reset();
        }
        return teamViewController.getRoot();
    }

    /**
     * A method for loading the hall of fame view
     * @param fxmlFile
     *          the fxml file for the hallOfFameView
     * @return a reference to the hall of fame view's value for the region element
     */
    private Region loadHallOfFameView(String fxmlFile) {
        if (hallOfFameViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
               hallOfFameViewController = loader.getController();
                hallOfFameViewController.init(this, viewModelFactory.getHallOfFameViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
           hallOfFameViewController.reset();
        }
        return hallOfFameViewController.getRoot();
    }
}
