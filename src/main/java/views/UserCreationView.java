package views;

import controllers.MainController;
import controllers.UserCreationController;
import controllers.UsersController;
import javax.swing.*;
import java.awt.*;

/**
 * UserCreationView is responsible for displaying the UI for creating a new user
 * profile.
 * It allows the user to input user details and save them.
 */
public class UserCreationView {
    private final JPanel panel;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField ageField;
    private final JTextField weightField;
    private final JTextField heightField;
    private final MainController mainController;
    private final UserCreationController userCreationController;

    public UserCreationView(MainController controller, UsersController usersController) {
        this.mainController = controller;
        userCreationController = new UserCreationController(this, usersController);
        panel = new JPanel(new GridBagLayout());
        Utils.setupModernUI();
        JLabel titleLabel = new JLabel("Create New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        nameField = new JTextField(15);
        surnameField = new JTextField(15);
        ageField = new JTextField(5);
        weightField = new JTextField(5);
        heightField = new JTextField(5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> userCreationController.saveUser());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> showUsersView());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(titleLabel, gbc);
        panel.add(new JLabel("Name:"), gbc);
        panel.add(nameField, gbc);
        panel.add(new JLabel("Surname:"), gbc);
        panel.add(surnameField, gbc);
        panel.add(new JLabel("Age:"), gbc);
        panel.add(ageField, gbc);
        panel.add(new JLabel("Weight (kg):"), gbc);
        panel.add(weightField, gbc);
        panel.add(new JLabel("Height (cm):"), gbc);
        panel.add(heightField, gbc);
        panel.add(saveButton, gbc);
        panel.add(cancelButton, gbc);
    }

    /**
     * Returns the main panel of the UserCreationView.
     *
     * @return the main panel
     */
    public JPanel getPanel() {
        return panel;
    }

    // Returns to the UsersView.
    private void showUsersView() {
        mainController.showUsersView();
        clearFields();
    }

    /**
     * Clears the input fields in the UserCreationView.
     */
    public void clearFields() {
        nameField.setText("");
        surnameField.setText("");
        ageField.setText("");
        weightField.setText("");
        heightField.setText("");
    }

    /**
     * Returns the name field.
     * 
     * @return the name field
     */
    public JTextField getNameField() {
        return nameField;
    }

    /**
     * 
     * /**
     * Returns the surname field.
     *
     * @return the surname field
     */
    public JTextField getSurnameField() {
        return surnameField;
    }

    /**
     * Returns the age field.
     *
     * @return the age field
     */
    public JTextField getAgeField() {
        return ageField;
    }

    /**
     * Returns the weight field.
     *
     * @return the weight field
     */
    public JTextField getWeightField() {
        return weightField;
    }

    /**
     * Returns the height field.
     *
     * @return the height field
     */
    public JTextField getHeightField() {
        return heightField;
    }

    /**
     * Returns the main controller.
     *
     * @return the main controller
     */
    public MainController getMainController() {
        return mainController;
    }
}
