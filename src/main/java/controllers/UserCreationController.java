package controllers;

import models.UserProfile;
import views.UserCreationView;
import views.Utils;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class UserCreationController {
    private final UserCreationView view;
    private static final String FILE_PATH = Utils.USER_PROFILES_PATH;
    public UserCreationController(UserCreationView userCreationView) {
        this.view = userCreationView;
    }
    public void saveUser() {
        try {
            String name = view.getNameField().getText();
            String surname = view.getSurnameField().getText();
            int age = Integer.parseInt(view.getAgeField().getText());
            double weight = Double.parseDouble(view.getWeightField().getText());
            double height = Double.parseDouble(view.getHeightField().getText());

            UserProfile userProfile = new UserProfile(name, surname, age, weight, height);

            saveUserToCSV(userProfile);
            System.out.println("User saved: " + userProfile);

            view.clearFields();
            view.getMainController().showUsersView();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error saving user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveUserToCSV(UserProfile userProfile) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(userProfile.getName()).append(",");
            writer.append(userProfile.getSurname()).append(",");
            writer.append(String.valueOf(userProfile.getAge())).append(",");
            writer.append(String.valueOf(userProfile.getWeight())).append(",");
            writer.append(String.valueOf(userProfile.getHeight())).append("\n");
        }
    }
}
