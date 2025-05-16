package views;

import controllers.EditExerciseController;
import controllers.ExercisesController;
import controllers.MainController;
import models.Exercise;
import javax.swing.*;
import java.awt.*;

/**
 * EditExerciseView is responsible for displaying the UI for editing an exercise.
 * It allows the user to input exercise details and save them.
 */
public class EditExerciseView {
    private final JPanel panel;
    private final JTextField nameField;
    private final JTextField typeField;
    private final JTextField muscleGroupField;
    private final JTextField repetitionsField;
    private final JTextField setsField;
    private final MainController mainController;
    private final EditExerciseController editExerciseController;

    public EditExerciseView(MainController controller, ExercisesController exercisesController) {
        this.mainController = controller;
        this.editExerciseController = new EditExerciseController(mainController, this);

        panel = new JPanel(new GridBagLayout());
        Utils.setupModernUI();

        JLabel titleLabel = new JLabel("Edit Exercise");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        nameField = new JTextField(15);
        typeField = new JTextField(15);
        muscleGroupField = new JTextField(15);
        repetitionsField = new JTextField(5);
        setsField = new JTextField(5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> editExerciseController.saveEditedExercise());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> mainController.showExercisesView());

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
     * Returns the panel containing the edit exercise UI.
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
    public Exercise getExerciseFromFields() {
        String name = nameField.getText();
        String type = typeField.getText();
        String muscleGroup = muscleGroupField.getText();
        int repetitions = Integer.parseInt(repetitionsField.getText());
        int sets = Integer.parseInt(setsField.getText());

        return new Exercise(name, type, muscleGroup, repetitions, sets);
    }

    /**
     * Sets the input fields with the details of the given exercise.
     *
     * @param exercise the Exercise object to set in the fields
     */
    public void setExerciseFields(Exercise exercise) {
        nameField.setText(exercise.getName());
        typeField.setText(exercise.getType());
        muscleGroupField.setText(exercise.getMuscleGroup());
        repetitionsField.setText(String.valueOf(exercise.getRepetitions()));
        setsField.setText(String.valueOf(exercise.getSets()));
    }
}
