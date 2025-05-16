package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import models.UserProfile;
import views.Utils;

/**
 * Controller for managing user profiles.
 */
public class UsersController {
    public DefaultListModel<UserProfile> usersList;

    public UsersController() {
        usersList = new DefaultListModel<>();
    }

    /**
     * Loads user profiles from a CSV file into the users list.
     *
     * @param panel    The panel to show error messages.
     * @param fileName The name of the CSV file to load.
     */
    public void loadUsersFromCSV(JPanel panel, String fileName) {
        // Clear the existing list before loading new data
        usersList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by commas to extract user data
                String[] userData = line.split(",");
                if (userData.length == 5) { // Ensure all fields are available
                    String name = userData[0];
                    String surname = userData[1];
                    int age = Integer.parseInt(userData[2]);
                    double weight = Double.parseDouble(userData[3]);
                    double height = Double.parseDouble(userData[4]);

                    // Create a UserProfile object and add to the list
                    UserProfile userProfile = new UserProfile(name, surname, age, weight, height);
                    usersList.addElement(userProfile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading users from CSV", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes a user profile from the users list and updates the CSV file.
     *
     * @param user The user profile to delete.
     */
    public void deleteUser(UserProfile user) {
        usersList.removeElement(user);
        Utils.saveUsersToCSV(Utils.USER_PROFILES_PATH, usersList);
    }
}
