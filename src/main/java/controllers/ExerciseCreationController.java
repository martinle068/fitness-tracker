package controllers;

import models.Exercise;
import views.ExerciseCreationView;
import views.Utils;
import javax.swing.*;
import java.io.IOException;

/**
 * Controller for creating and saving exercises.
 */
public class ExerciseCreationController {
    private final ExerciseCreationView view;
    private final ExercisesController exercisesController;

    public ExerciseCreationController(ExerciseCreationView view, ExercisesController exercisesController) {
        this.view = view;
        this.exercisesController = exercisesController;
    }

    /**
     * Saves the exercise created in the view to a CSV file and updates the exercises list.
     */
    public void saveExercise() {
        try {
            String name = view.getNameField().getText();
            String type = view.getTypeField().getText();
            String muscleGroup = view.getMuscleGroupField().getText();
            int repetitions = Integer.parseInt(view.getRepetitionsField().getText());
            int sets = Integer.parseInt(view.getSetsField().getText());

            Exercise exercise = new Exercise(name, type, muscleGroup, repetitions, sets);

            Utils.saveExerciseToCSV(Utils.EXERCISES_PATH, exercise);
            System.out.println("Exercise saved: " + exercise);

            exercisesController.exercisesList.addElement(exercise);
            view.clearFields();
            view.getMainController().showExercisesView();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Invalid number input!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error saving exercise!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
