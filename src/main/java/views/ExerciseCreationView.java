package views;

import controllers.ExercisesController;
import controllers.ExerciseCreationController;
import controllers.MainController;

import javax.swing.*;
import java.awt.*;

public class ExerciseCreationView {
    private final JPanel panel;
    private final JTextField nameField;
    private final JTextField typeField;
    private final JTextField muscleGroupField;
    private final JTextField repetitionsField;
    private final JTextField setsField;
    private final MainController mainController;
    private final ExerciseCreationController exerciseCreationController;

    public ExerciseCreationView(MainController controller, ExercisesController exercisesController) {
        this.mainController = controller;
        this.exerciseCreationController = new ExerciseCreationController(this, exercisesController);

        panel = new JPanel(new GridBagLayout());
        Utils.setupModernUI();

        JLabel titleLabel = new JLabel("Create New Exercise");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        nameField = new JTextField(15);
        typeField = new JTextField(15);
        muscleGroupField = new JTextField(15);
        repetitionsField = new JTextField(5);
        setsField = new JTextField(5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> exerciseCreationController.saveExercise());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> showExercisesView());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(titleLabel, gbc);
        panel.add(new JLabel("Name:"), gbc);
        panel.add(nameField, gbc);
        panel.add(new JLabel("Type:"), gbc);
        panel.add(typeField, gbc);
        panel.add(new JLabel("Muscle Group:"), gbc);
        panel.add(muscleGroupField, gbc);
        panel.add(new JLabel("Repetitions:"), gbc);
        panel.add(repetitionsField, gbc);
        panel.add(new JLabel("Sets:"), gbc);
        panel.add(setsField, gbc);
        panel.add(saveButton, gbc);
        panel.add(cancelButton, gbc);
    }

    private void showExercisesView() {
        mainController.showExercisesView();
        clearFields();
    }

    public void clearFields() {
        nameField.setText("");
        typeField.setText("");
        muscleGroupField.setText("");
        repetitionsField.setText("");
        setsField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getTypeField() {
        return typeField;
    }

    public JTextField getMuscleGroupField() {
        return muscleGroupField;
    }

    public JTextField getRepetitionsField() {
        return repetitionsField;
    }

    public JTextField getSetsField() {
        return setsField;
    }

    public MainController getMainController() {
        return mainController;
    }
}
