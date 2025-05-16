package controllers;

import models.Exercise;
import views.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Controller for managing exercises.
 */
public class ExercisesController {
    public DefaultListModel<Exercise> exercisesList;

    public ExercisesController() {
        exercisesList = new DefaultListModel<>();

    }

    /**
     * Loads exercises from a CSV file into the exercises list.
     *
     * @param panel    The panel to show error messages.
     * @param fileName The name of the CSV file to load.
     */
    public void loadExercisesFromCSV(JPanel panel, String fileName) {
        exercisesList.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] exerciseData = line.split(",");
                if (exerciseData.length == 5) {
                    String name = exerciseData[0];
                    String type = exerciseData[1];
                    String muscleGroup = exerciseData[2];
                    int repetitions = Integer.parseInt(exerciseData[3]);
                    int sets = Integer.parseInt(exerciseData[4]);

                    Exercise exercise = new Exercise(name, type, muscleGroup, repetitions, sets);
                    exercisesList.addElement(exercise);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading exercises from CSV", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes an exercise from the exercises list and updates the CSV file.
     *
     * @param exercise The exercise to delete.
     */
    public void deleteExercise(Exercise exercise) {
        exercisesList.removeElement(exercise);
        // Save the updated list to CSV
        Utils.saveExercisesToCSV(Utils.EXERCISES_PATH, exercisesList);
    }
}
