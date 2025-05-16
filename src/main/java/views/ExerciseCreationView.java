package views;

import controllers.ExercisesController;
import controllers.ExerciseCreationController;
import controllers.MainController;
import javax.swing.*;
import java.awt.*;

/**
 * ExerciseCreationView is responsible for displaying the UI for creating a new exercise.
 * It allows the user to input exercise details and save them.
 */
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

    /**
     * Returns to the ExercisesView.
     */
    private void showExercisesView() {
        mainController.showExercisesView();
        clearFields();
    }

    /**
     * Clears the input fields in the ExerciseCreationView.
     */
    public void clearFields() {
        nameField.setText("");
        typeField.setText("");
        muscleGroupField.setText("");
        repetitionsField.setText("");
        setsField.setText("");
    }

    /**
     * Returns the panel containing the exercise creation UI.
     *
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Retrieves the exercise details from the input fields and creates an Exercise object.
     *
     * @return the Exercise object with the input details
     */
    public JTextField getNameField() {
        return nameField;
    }

    /**
     * Retrieves the exercise type from the input field.
     *
     * @return the exercise type
     */
    public JTextField getTypeField() {
        return typeField;
    }

    /**
     * Retrieves the muscle group from the input field.
     *
     * @return the muscle group
     */
    public JTextField getMuscleGroupField() {
        return muscleGroupField;
    }

    /**
     * Retrieves the number of repetitions from the input field.
     *
     * @return the number of repetitions
     */
    public JTextField getRepetitionsField() {
        return repetitionsField;
    }

    /**
     * Retrieves the number of sets from the input field.
     *
     * @return the number of sets
     */
    public JTextField getSetsField() {
        return setsField;
    }

    /**
     * Returns the main controller associated with this view.
     *
     * @return the main controller
     */
    public MainController getMainController() {
        return mainController;
    }
}
