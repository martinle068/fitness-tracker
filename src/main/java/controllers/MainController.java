package controllers;

import models.UserProfile;
import views.MainView;
import views.UserCreationView;

import java.util.List;

public class MainController {
    private final MainView mainView;

    public MainController() {
        this.mainView = new MainView(this);
    }

    public void start() {
        mainView.displayMainMenu();
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
}