package controllers;

import views.Utils;
import javax.swing.JOptionPane;
import views.EditExerciseView;

/**
 * EditExerciseController is responsible for handling the logic of editing an exercise.
 * It interacts with the EditExerciseView and the MainController to save the edited exercise.
 */
public class EditExerciseController {
    private final MainController mainController;
    private final EditExerciseView editExerciseView;

    public EditExerciseController(MainController mainController, EditExerciseView editExerciseView) {
        this.mainController = mainController;
        this.editExerciseView = editExerciseView;
    }
    
    /**
     * Saves the edited exercise by updating the CSV file and refreshing the exercises view.
     */
    public void saveEditedExercise() {
        try {
            Utils.updateExerciseInCSV(Utils.EXERCISES_PATH, this.mainController.selectedExercise,
                    editExerciseView.getExerciseFromFields());
            mainController.exercisesController.loadExercisesFromCSV(mainController.exercisesView.getPanel(),
                    Utils.EXERCISES_PATH);
            mainController.showExercisesView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editExerciseView.getPanel(), "Error saving exercise!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
