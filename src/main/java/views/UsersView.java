package views;

import controllers.MainController;
import models.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UsersView {
    private DefaultListModel<UserProfile> usersList;
    private final JPanel panel;
    private final MainController controller;

    public UsersView(MainController controller) {
        this.controller = controller;
        usersList = new DefaultListModel<>();

        // Load users from CSV and populate the list
        loadUsersFromCSV(Utils.USER_PROFILES_PATH);

        JList<UserProfile> list = new JList<>(usersList);
        list.setCellRenderer((list1, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.toString());
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            if (isSelected) {
                label.setBackground(new Color(100, 149, 237)); // Set selection color
                label.setOpaque(true);
            }
            return label;
        });

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230));

        JLabel titleLabel = new JLabel("User Profiles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> this.controller.showMainMenu());

        // Add "Create User" button
        JButton createUserButton = new JButton("Create User");
        createUserButton.setFont(new Font("Arial", Font.BOLD, 18));
        createUserButton.addActionListener(e -> this.controller.showUserCreationView());

        // Create a sub-panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(createUserButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadUsersFromCSV(String fileName) {
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

    public JPanel getPanel() {
        return panel;
    }
}
