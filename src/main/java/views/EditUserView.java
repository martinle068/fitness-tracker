package views;
import controllers.EditUserController;
import controllers.MainController;
import controllers.UsersController;
import models.UserProfile;
import javax.swing.*;
import java.awt.*;

/**
 * EditUserView is responsible for displaying the UI for editing a user profile.
 * It allows the user to input user details and save them.
 */
public class EditUserView {
    private final JPanel panel;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField ageField;
    private final JTextField weightField;
    private final JTextField heightField;
    private final MainController mainController;
    private final EditUserController editUserController;

    public EditUserView(MainController controller, UsersController usersController) {
        this.mainController = controller;
        editUserController = new EditUserController(mainController, this);
        panel = new JPanel(new GridBagLayout());
        Utils.setupModernUI();
        JLabel titleLabel = new JLabel("Edit User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        nameField = new JTextField(15);
        surnameField = new JTextField(15);
        ageField = new JTextField(5);
        weightField = new JTextField(5);
        heightField = new JTextField(5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> editUserController.saveEditedUser());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> mainController.showUsersView());

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
     * Returns the main panel of the EditUserView.
     *
     * @return the main panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Retrieves the user profile details from the input fields.
     *
     * @return a UserProfile object containing the input data
     */
    public UserProfile getUserProfileFromFields() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        int age = Integer.parseInt(ageField.getText());
        double weight = Double.parseDouble(weightField.getText());
        double height = Double.parseDouble(heightField.getText());

        return new UserProfile(name, surname, age, weight, height);
    }
    
    /**
     * Sets the input fields with the details of the provided user profile.
     */
    public void setUserProfileFields(UserProfile userProfile) {
        nameField.setText(userProfile.getName());
        surnameField.setText(userProfile.getSurname());
        ageField.setText(String.valueOf(userProfile.getAge()));
        weightField.setText(String.valueOf(userProfile.getWeight()));
        heightField.setText(String.valueOf(userProfile.getHeight()));
    }
}
