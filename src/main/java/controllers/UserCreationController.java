package controllers;

import models.UserProfile;
import views.UserCreationView;
import views.Utils;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class UserCreationController {
    private final UserCreationView view;
    private final UsersController usersController;
    private static final String FILE_PATH = Utils.USER_PROFILES_PATH;
    public UserCreationController(UserCreationView userCreationView, UsersController usersController) {
        this.view = userCreationView;
        this.usersController = usersController;
    }
    public void saveUser() {
        try {
            String name = view.getNameField().getText();
            String surname = view.getSurnameField().getText();
            int age = Integer.parseInt(view.getAgeField().getText());
            double weight = Double.parseDouble(view.getWeightField().getText());
            double height = Double.parseDouble(view.getHeightField().getText());

            UserProfile userProfile = new UserProfile(name, surname, age, weight, height);

            Utils.saveUserToCSV(FILE_PATH, userProfile);
            System.out.println("User saved: " + userProfile);

            usersController.usersList.addElement(userProfile);
            view.clearFields();
            view.getMainController().showUsersView();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error saving user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
