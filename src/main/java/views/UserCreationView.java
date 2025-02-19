package views;

import controllers.MainController;
import controllers.UserCreationController;

import javax.swing.*;
import java.awt.*;

public class UserCreationView {
    private final JPanel panel;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField ageField;
    private final JTextField weightField;
    private final JTextField heightField;
    private final MainController mainController;
    private final UserCreationController userCreationController;

    public UserCreationView(MainController controller) {
        this.mainController = controller;
        userCreationController = new UserCreationController(this);
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

    public JPanel getPanel() {
        return panel;
    }

    private void showUsersView(){
        mainController.showUsersView();
        clearFields();
    }

    public void clearFields() {
        nameField.setText("");
        surnameField.setText("");
        ageField.setText("");
        weightField.setText("");
        heightField.setText("");
    }

    public MainController getMainController() {
        return mainController;
    }

    public JTextField getNameField() {
        return nameField;
    }
    public JTextField getSurnameField() {
        return surnameField;
    }
    public JTextField getAgeField() {
        return ageField;
    }
    public JTextField getWeightField() {
        return weightField;
    }
    public JTextField getHeightField() {
        return heightField;
    }
}
