package views;

import controllers.EditExerciseController;
import controllers.ExercisesController;
import controllers.MainController;
import models.Exercise;

import javax.swing.*;
import java.awt.*;

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
        this.editExerciseController = new EditExerciseController(mainController, this, exercisesController);

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

    public JPanel getPanel() {
        return panel;
    }

    public void showExercisesView() {
        mainController.showExercisesView();
    }

    public Exercise getExerciseFromFields() {
        String name = nameField.getText();
        String type = typeField.getText();
        String muscleGroup = muscleGroupField.getText();
        int repetitions = Integer.parseInt(repetitionsField.getText());
        int sets = Integer.parseInt(setsField.getText());

        return new Exercise(name, type, muscleGroup, repetitions, sets);
    }

    public void setExerciseFields(Exercise exercise) {
        nameField.setText(exercise.getName());
        typeField.setText(exercise.getType());
        muscleGroupField.setText(exercise.getMuscleGroup());
        repetitionsField.setText(String.valueOf(exercise.getRepetitions()));
        setsField.setText(String.valueOf(exercise.getSets()));
    }
}
