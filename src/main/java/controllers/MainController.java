package controllers;

import models.UserProfile;
import views.EditUserView;
import views.MainView;
import views.UserCreationView;
import views.UsersView;

public class MainController {
    private final MainView mainView;
    public final UsersView usersView;
    public final UserCreationView userCreationView; // Add UserCreationView
    public final EditUserView editUserView;
    public final UsersController usersController;
    public UserProfile selectedUserProfile = null;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        this.usersController = new UsersController();
        this.usersView = new UsersView(this, usersController); // Initialize UsersView
        this.userCreationView = new UserCreationView(this, usersController); // Initialize UserCreationView
        this.editUserView = new EditUserView(this, usersController); // Initialize EditUserView
    }

    public void showMainMenu() {
        mainView.showView("MainMenu");
    }

    public void showUsersView() {
        mainView.showView("UsersView");
    }

    public void showUserCreationView() {
        mainView.showView("UserCreationView"); // Show the UserCreationView
    }

    public void showEditUserView(UserProfile userProfile) {
        this.selectedUserProfile = userProfile; // Store the selected user profile
        editUserView.setUserProfileFields(userProfile); // Set the user profile in the EditUserView
        mainView.showView("EditUserView");
    }
}